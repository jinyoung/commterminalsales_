package commterminalsales.domain;

import commterminalsales.domain.*;
import commterminalsales.infra.AbstractEvent;
import java.util.*;
import lombok.*;

@Data
@ToString
public class SpecCompared extends AbstractEvent {

    private Long id;
    private Object comparations;
    private String customerId;
}
