# 🎬 AvII_Mobile_Films
Aplicativo Android desenvolvido em Kotlin para o gerenciamento de um catálogo pessoal de filmes, aplicando persistência local com SQLite, navegação entre telas e operações CRUD completas.

## Sobre o Projeto
Desenvolvido como o Avaliação II da disciplina de desenvolvimento Android, a aplicação permite aos usuários cadastrar, organizar, consultar e filtrar sua coleção de filmes de forma prática e intuitiva. Os dados são armazenados localmente, garantindo persistência mesmo após fechar o aplicativo ou reiniciar o dispositivo.

## Funcionalidades e Regras de Negócio
- **CRUD Completo:** Inserção, listagem, atualização e exclusão de filmes.
- **Regra de Negócio Customizada:** Controle de status do filme (ex: "Assistido" ou "Quero Assistir") e validação de campos obrigatórios e duração.
- **Filtros e Ordenação:**
  - Listar todos os registros.
  - Pesquisa por nome do filme.
  - Filtro por gênero cinematográfico.
  - Ordenação alfabética por nome.

## Arquitetura do Projeto
O projeto segue uma estrutura organizada em camadas bem definidas:
- **Model:** Classes de dados que representam a entidade Filme.
- **DBHelper:** Extensão de `SQLiteOpenHelper` responsável pela criação e gerenciamento da versão do banco de dados local.
- **DAO & Controllers:** Centralização das operações SQL e lógica de negócio intermediária entre as telas e os dados.

## Tecnologias Utilizadas
- **Linguagem:** Kotlin
- **Ambiente:** Android Studio (API 29)
- **Banco de Dados:** SQLite
- **Interface:** XML (Layouts nativos e componentes de lista)

## 👥 Integrantes da Equipe
- **Amanda Lima** GRR20245459
- **Caio Vitor** GRR20253420
- **Ender Ruiz** GRR20250037
- **Jhoslaine Avila** GRR20231021

---
Trabalho acadêmico desenvolvido para a disciplina DS151.
