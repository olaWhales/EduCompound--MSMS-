package org.example.sessionPackage.sessionService;

import org.example.sessionPackage.dto.sessionRequest.TermRequest;
import org.example.sessionPackage.dto.sessionResponse.TermResponse;

public interface TermCreateService {
    TermResponse create(TermRequest termRequest);
}
