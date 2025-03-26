package gov.saip.applicationservice.common.enums.agency;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
public enum TrademarkAgencyStatusCode {

    NEW(1),
    UNDER_PROCEDURE(2),
    REQUEST_CORRECTION(3),
    REJECTED(4),
    ACCEPTED(5),
    EXPIRED(6);

    private Integer id;


    public static List<Integer> getPreviousRequestsIdsList() {
        List<Integer> list = new ArrayList<>();
        list.add(ACCEPTED.id);
        list.add(REJECTED.id);
        list.add(EXPIRED.id);
        return list;
    }

    public static List<Integer> getCurrentRequestsIdsList() {
        List<Integer> list = new ArrayList<>();
        list.add(NEW.id);
        list.add(UNDER_PROCEDURE.id);
        list.add(REQUEST_CORRECTION.id);
        return list;
    }
}
