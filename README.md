# ConectaUfc

**ConectaUfc** é uma plataforma web desenvolvida para armazenar e compartilhar materiais acadêmicos da Universidade Federal do Ceará (UFC). A plataforma tem como objetivo fornecer aos alunos o acesso a materiais utilizados pelos professores durante as aulas, como slides e provas passadas, para que possam utilizar esses recursos em seus estudos.

O sistema permite que apenas os monitores de cada disciplina possam fazer upload e deletar materiais, enquanto os alunos têm apenas permissão para visualizar os materiais disponíveis de qualquer disciplina.

## Funcionalidades

- **Upload e Deleção de Materiais**: Apenas monitores possuem permissão para realizar o upload e a deleção de materiais de cada cadeira (disciplinas).
- **Acesso aos Materiais**: Os alunos podem acessar todos os materiais disponíveis para as disciplinas em que estão matriculados.
- **Autenticação e Autorização**: Sistema de autenticação e autorização utilizando tokens JWT com Spring Security.
- **Visualização de Materiais**: Suporte para visualização de diferentes tipos de arquivos, como slides e provas passadas.

## Tecnologias Utilizadas

- **Java**: Linguagem de programação principal.
- **Spring Framework**: Framework utilizado para desenvolvimento da aplicação, proporcionando uma estrutura robusta e escalável.
- **Spring Security**: Utilizado para implementar a segurança da aplicação, incluindo a autenticação com tokens JWT.
- **PostgreSQL**: Banco de dados relacional utilizado para armazenar os dados da aplicação, incluindo os materiais dos professores.
- **Swagger**: foi integrado para facilitar a documentação e o teste da API.
