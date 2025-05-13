# 📄 Sistema de Holerite

Esse é um projeto **MVC** desenvolvido com **Java**, **Spring Boot**, **PostgreSQL**, e integrações como **IText** para geração de PDF e **JavaMailSender** para envio por e-mail.

O sistema foi criado com o objetivo de permitir a geração automatizada de holerites, desde o cadastro do funcionário até o envio do seu contracheque em PDF para o e-mail registrado.

---

## 🛠 Tecnologias Utilizadas

- Java 17+
- Spring Boot
- Spring Data JPA
- PostgreSQL
- IText (PDF)
- JavaMailSender (e-mail)
- Thymeleaf (caso use para views)
- Maven

---

## 🔐 Perfis de Usuário

Existem **dois tipos de usuário** no sistema:

### 👑 Admin

Tem acesso total às funcionalidades:

- ➕ Criar funcionário
- ➕ Criar holerite
- ✏️ Editar salário bruto do funcionário
- 👁️ Ver todos os holerites de um funcionário
- 👁️ Ver todos os funcionários existentes
- ❌ Demitir (deletar) funcionário

### 👤 Funcionário

Apenas pode:

- 📄 Visualizar seus próprios holerites
- 🚪 Realizar logout

---

## 📬 Envio de Holerite por E-mail

Ao gerar um holerite, ele é automaticamente:

1. Gerado em PDF usando a biblioteca **IText**
2. Enviado para o e-mail do funcionário com o arquivo em anexo, via **JavaMailSender**

---

## 💾 Banco de Dados

O projeto usa **PostgreSQL** para persistência das seguintes entidades:

- Funcionário (nome, salário bruto, dependentes, etc.)
- Holerite (descontos de INSS, IRRF, vale transporte, salário líquido, data, etc.)

