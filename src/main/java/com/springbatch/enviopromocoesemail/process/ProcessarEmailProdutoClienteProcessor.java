package com.springbatch.enviopromocoesemail.process;

import java.text.NumberFormat;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

import com.springbatch.enviopromocoesemail.domain.InteresseProdutoCliente;

@Component
public class ProcessarEmailProdutoClienteProcessor implements ItemProcessor<InteresseProdutoCliente, SimpleMailMessage> {

	@Override
	public SimpleMailMessage process(InteresseProdutoCliente interesseProdutoCliente) throws Exception {
		SimpleMailMessage email = new SimpleMailMessage();
		email.setFrom("email@no-reply.com");
		email.setTo(interesseProdutoCliente.getCliente().getEmail());
		email.setSubject("Promoção!");
		email.setText(gerarTextoPromocao(interesseProdutoCliente));
		Thread.sleep(2000);
		
		return email;
	}

	private String gerarTextoPromocao(InteresseProdutoCliente interesseProdutoCliente) {
		StringBuilder writer = new StringBuilder();
		writer.append(String.format("Oi, %s!\n\n", interesseProdutoCliente.getCliente().getNome()));
		writer.append("Esta promoção pode ser do seu interesse:\n\n");
		writer.append(String.format("%s - %s\n\n", interesseProdutoCliente.getProduto().getNome(), interesseProdutoCliente.getProduto().getDescricao()));
		writer.append(String.format("Por apenas: %s!",
				NumberFormat.getCurrencyInstance().format(interesseProdutoCliente.getProduto().getPreco())));
		
		return writer.toString();
	}
}
