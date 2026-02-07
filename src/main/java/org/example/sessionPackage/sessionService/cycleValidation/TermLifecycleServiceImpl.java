package org.example.sessionPackage.sessionService.cycleValidation;

import lombok.RequiredArgsConstructor;
import org.example.data.model.Term;
import org.example.data.model.TermStatus;
import org.example.data.repositories.TermRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class TermLifecycleServiceImpl implements TermLifecycleService {

    private final TermRepository termRepository;
    private final Clock clock; // timezone-safe

    @Override
    public void synchronizeSessionStatuses() {

        LocalDate today = LocalDate.now(clock);

        List<Term> terms = termRepository.findAll();

        for (Term term : terms) {

            // PLANNED → ACTIVE
            if (term.getStatus() == TermStatus.PLANNED &&
                    !today.isBefore(
                            term.getStartDate()
                                    .toInstant()
                                    .atZone(ZoneId.systemDefault())
                                    .toLocalDate()
                    )) {

                term.setStatus(TermStatus.ACTIVE);
            }

            // ACTIVE → COMPLETED (endDate passed)
            if (term.getStatus() == TermStatus.ACTIVE &&
                    today.isAfter(
                            term.getEndDate()
                                    .toInstant()
                                    .atZone(ZoneId.systemDefault())
                                    .toLocalDate()
                    )) {
                term.setStatus(TermStatus.COMPLETED);
            }
        }
    }
}
