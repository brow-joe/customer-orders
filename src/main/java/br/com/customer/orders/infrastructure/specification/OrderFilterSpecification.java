package br.com.customer.orders.infrastructure.specification;

import br.com.customer.orders.domain.gateway.IRetrieveOrdersGateway;
import br.com.customer.orders.infrastructure.entity.OrderEntity;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Objects;

@Component
public class OrderFilterSpecification {
    public Specification<OrderEntity> apply(IRetrieveOrdersGateway.Filter filter) {
        Specification<OrderEntity> specification = Specification.where(null);
        if (StringUtils.isNotEmpty(filter.numberControl())) {
            specification = specification.and(numberControlILike(filter.numberControl()));
        }
        if (Objects.nonNull(filter.registrationDateStart())) {
            specification = specification.and(registrationDateGreaterThanOrEqualTo(filter.registrationDateStart()));
        }
        if (Objects.nonNull(filter.registrationDateEnd())) {
            specification = specification.and(registrationDateLessThanOrEqualTo(filter.registrationDateEnd()));
        }
        return specification;
    }

    private Specification<OrderEntity> numberControlILike(String numberControl) {
        return (root, _, builder) -> builder.like(builder.upper(root.get("numberControl")), likeTerm(numberControl));
    }

    private Specification<OrderEntity> registrationDateGreaterThanOrEqualTo(LocalDateTime registrationDate) {
        return (root, _, builder) -> builder.greaterThanOrEqualTo(root.get("registrationDate"), registrationDate);
    }

    private Specification<OrderEntity> registrationDateLessThanOrEqualTo(LocalDateTime registrationDate) {
        return (root, _, builder) -> builder.lessThanOrEqualTo(root.get("registrationDate"), registrationDate);
    }

    private String likeTerm(String term) {
        return new StringBuilder().append('%').append(term.trim().toUpperCase()).append('%').toString();
    }
}