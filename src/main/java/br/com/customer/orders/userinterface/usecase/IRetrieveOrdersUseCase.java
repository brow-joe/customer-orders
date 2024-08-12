package br.com.customer.orders.userinterface.usecase;

import br.com.customer.orders.domain.gateway.IRetrieveOrdersGateway;
import br.com.customer.orders.userinterface.vo.OrderVO;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.List;

@FunctionalInterface
public interface IRetrieveOrdersUseCase {
    List<OrderVO> execute(Filter filter);
    
    record Filter(
            @Schema(description = "Faz a busca pelo numero de controle")
            String numeroControle,
            @Schema(description = "Faz a busca pela data de cadastro de")
            LocalDateTime dataCadastroDe,
            @Schema(description = "Faz a busca pela data de cadastro até")
            LocalDateTime dataCadastroAte
    ) implements IRetrieveOrdersGateway.Filter {
        @Override
        public String numberControl() {
            return numeroControle;
        }
        
        @Override
        public LocalDateTime registrationDateStart() {
            return dataCadastroDe;
        }

        @Override
        public LocalDateTime registrationDateEnd() {
            return dataCadastroAte;
        }
    }
}