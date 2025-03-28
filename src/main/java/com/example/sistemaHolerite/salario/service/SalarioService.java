package com.example.sistemaHolerite.salario.service;

import com.example.sistemaHolerite.funcionario.model.FuncionarioModel;
import com.example.sistemaHolerite.funcionario.repository.FuncionarioRepository;
import com.example.sistemaHolerite.salario.repository.SalarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class SalarioService {

    @Autowired
    private SalarioRepository salarioRepository;

    @Autowired
    private FuncionarioRepository funcionarioRepository;


    public String dataHoraHolerite(LocalDateTime agora){

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm:ss");

        return agora.format(formatter);
    }

    /*
        Tabela de alíquotas INSS 2025
        Até R$ 1.518,00: 7,5%
        De R$ 1.518,01 até R$ 2.793,88: 9%
        De R$ 2.793,89 até R$ 4.190,83: 12%
        De R$ 4.190,84 até R$ 8.157,41: 14%
        teto máximo = R$ 8157,41
     */

    public Double inss(Long id){
        FuncionarioModel funcionario = funcionarioRepository.findById(id).orElseThrow();
        Double salarioBruto = funcionario.getSalarioBruto();

        if(salarioBruto < 1518.00) return salarioBruto * 0.075;
        else if(salarioBruto < 2793.88) return salarioBruto * 0.09;
        else if(salarioBruto < 4190.83) return salarioBruto * 0.12;
        else if(salarioBruto < 8157.41) return salarioBruto * 0.14;
        else return 8157.41 * 0.14;
    }

    /*
    Base de cálculo (R$)	        Alíquota (%)	Parcela a deduzir do IR (R$)
    2.259,21 até 2.826,65	            7,5%	                    169,44
    2.826,66 até 3.751,05	            15%	                        381,44
    3.751,06 até 4.664,68	            22,5%	                    662,77
    Acima de 4.664,68	                27,5%	                    896,00


    Base de Cálculo = Salário Bruto - INSS - Dependentes (se houver)
     */

    public Double irrf(Long id){
        FuncionarioModel funcionario = funcionarioRepository.findById(id).orElseThrow();
        Double salarioBruto = funcionario.getSalarioBruto();
        Double baseDeCalculoIrrf = salarioBruto - inss(id) - (funcionario.getDependentes() * 189.59);

        Double irrfValor;

        if(baseDeCalculoIrrf < 2259.21) irrfValor = 0.0;
        else if(baseDeCalculoIrrf < 2826.66) irrfValor = (baseDeCalculoIrrf * 0.075) - 169.44;
        else if(baseDeCalculoIrrf < 3751.06) irrfValor = (baseDeCalculoIrrf * 0.15) - 381.44;
        else if(baseDeCalculoIrrf >= 4664.68) irrfValor = (baseDeCalculoIrrf * 0.225) - 662.77;
        else irrfValor = (baseDeCalculoIrrf * 0.275) - 896.00;

        return irrfValor;
    }

    // calculo do desconto do vale transporte

    public Double valeTransporte(Long id){
        FuncionarioModel funcionario = funcionarioRepository.findById(id).orElseThrow();
        Double salarioBruto = funcionario.getSalarioBruto();

        if(funcionario.getTemValeTransporte()){
            if(salarioBruto * 0.06 > 300){
                return 300.00; // teto máximo do vale transporte
            }
            else return salarioBruto * 0.06;
        }
        else{
            return 0.0;
        }
    }

    // calculo salario liquido
    public Double salarioLiquido(Long id){
        FuncionarioModel funcionario = funcionarioRepository.findById(id).orElseThrow();
        Double salarioLiquido = funcionario.getSalarioBruto() - inss(id) - irrf(id) - valeTransporte(id);


        return salarioLiquido;
    }

}
