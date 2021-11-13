package cz.mg.test.exceptions;

import cz.mg.annotations.requirement.Mandatory;


public class DiscrepancyException extends RuntimeException {
    public DiscrepancyException(@Mandatory String message) {
        super(message);
    }
}
