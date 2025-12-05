# 🚀 Instruções da Migração para (Maven & JPA/Hibernate)

---

**⚠️ Aviso Importante sobre Testes:** Devido à urgência do prazo, alguns testes de integração ainda não foram concluídos. No entanto, a migração da arquitetura está finalizada com êxito e funcional.

Vou encaminhar os materiais para vocês estudarem e entenderem aquilo que foi implementado nesta "migração".

> **Observação:** Além dos materiais, adicionei comentários em alguns arquivos novos no código da aplicação, para caso de dúvidas. (Embora alguns materiais sejam mais antigos, o conceito das *stacks* continua o mesmo)

---

## 📚 Materiais de Estudo e Conceitos

Aqui estão os artigos e vídeos para aprofundar o entendimento sobre as tecnologias implementadas (ORM, JPA, Hibernate e Maven).

### 1. Conceitos de ORM, JPA e Hibernate

| Tópico | Recurso | Tipo | Nota |
| :--- | :--- | :--- | :--- |
| **ORM** | [DevMedia - ORM (Object Relational Mapper)](https://www.devmedia.com.br/orm-object-relational-mapper/19056) | Artigo | **Leiam**!! |
| **JPA com Hibernate** | [Medium - Entenda de uma vez por todas JPA e Hibernate](https://medium.com/@duduxss3/entenda-de-uma-vez-por-todas-jpa-e-hibernate-e2a1237161a9) | Artigo | **LEIAM ESTE ARTIGO!** ||
| **Build & Run** | [YouTube - Build & Run](https://youtu.be/4yj3mYAbNIA?si=Dmy67WCl84W9rUWf) | Vídeo |Conceito de Relacionamento entre a Anotation - @OneToMany e suas demais|
| **Base de JPA** | [YouTube - DevSuperior](https://youtu.be/CAP1IPgeJkw?si=Tyzb-t2RXfcsoCGN) | Vídeo | Aula antiga para pegar uma base |
| **ProfRogério** | [YouTube - ProfRogério](https://youtu.be/8qP06yVj5JU?si=CVmgA-Ubc51b3-ca) | Vídeo | **Vejam**!!|
| **Explicando JPA e persistence.xml** | [YouTube - Explicando JPA e persistence.xml](https://youtu.be/15psnxLYDo4?si=e3AW-_Yy4i-CnwCw) | Vídeo |  **Vejam**!! |

### 2. Maven com Java

| Tópico | Recurso | Tipo | Nota |
| :--- | :--- | :--- | :--- |
| **O que é o Maven** | [Medium - O que é o Maven e como instalar](https://medium.com/@januario86/o-que-%C3%A9-o-maven-e-como-instalar-1d5e9f29ac4c) | Artigo | **LEIAM!** |
| **Maven com Java** | [YouTube - Giuliana Bezzera](https://youtu.be/N7AyIfUQxGc?si=1GOfbU-mQYZfxU-z) | Vídeo | |
| **Conceito Maven** | [YouTube - Curso Tecnologia](https://youtu.be/bN6ZOjhrOm4?si=UcwOXHYmHOHddpjA) | Vídeo | |
| **Prof Thomás Costa** | [YouTube - Prof Thomás Costa](https://youtu.be/J9y5Spv8WsE?si=pGfdNxY9POt1a8S0) | Vídeo | |
| **Explicando pom.xml** | [Medium - Understanding pom.xml in Maven](https://anju-chaurasiya2012.medium.com/understanding-pom-xml-in-maven-a-beginner-friendly-guide-7f2e23232d4a) | Artigo | |

### 3. Repositório de Exemplo

* **Repositório no GitHub para auxiliar no entendimento:** [renandacruzzsnts/java-oficina-crud](https://github.com/renandacruzzsnts/java-oficina-crud/tree/main)

---

## 🛠️ Resumo das Alterações (O que foi mudado/removido)

A seguir, um resumo das principais mudanças de arquitetura e remoções de código:

### ❌ Itens Removidos

* **Dependências Manuais:**
    * Arquivos **.jar** (MySQL) e **libraries** (JavaFX) que eram adicionadas manualmente via `Project Structure/Modules/Dependencies`.
    * Classes de **Conexão** e **Teste de JDBC**.
    * O uso de **`PreparedStatement`** para abrir conexão com o banco.
    * Arquivo de configuração (**`config.properties`**).

### 🔄 Itens Alterados

* **Estrutura da Model:**
    * A model agora utiliza **`@Anotations`** do **Persistence (JPA)** para o mapeamento relacional.

---

## ✨ Oque há de novo?

Conheça os novos componentes e a nova organização do projeto:

### ⚙️ Gerenciamento e Configuração

* **`pom.xml` (Maven):**
    * Será o responsável por gerenciar as **dependências** e bibliotecas da nossa aplicação. Ele contém toda a configuração do projeto.
* **`persistence.xml` (JPA):**
    * Contém as **configurações de acesso ao banco** de dados (url, password, user, dialeto, etc.).

### 📦 Estrutura de Código

* **Root Package Único:**
    * Inclusão de um diretório de domínio único (`br.com.pi.achadoseperdidos`) no **root package** para identificação de importações e prevenção de conflitos de pacotes.
* **`JPAUtil`:**
    * Esta classe gerencia a **instanciação da conexão** (`EntityManagerFactory`) **uma única vez** para a aplicação toda, otimizando o processo (Função semelhante à antiga `ConexãoDB`).
* **Pasta `test`:**
    * Criada para incluir testes unitários e de integração das classes **`DAO`**/métodos.

### 🧩 Classes de Auxílio

* **Classes de Auxílio para Chave Composta (`Composite Key`):**
    * Classes criadas para lidar com o mapeamento de chaves compostas em tabelas de entidade relacional:

        * `ObjetoEncaminhadoId`
        * `RegistroFotoId`
        * `DevolucaoFotoId`

---

## ⚙🌐 Configuração Global de Ambiente e Boas Práticas de Build

Esta seção deve ser seguida por ***TODOS*** os desenvolvedores do projeto. Para que possamos utilizar os comandos do Maven (`mvn`) no terminal e nas ferramentas da IDE, é necessário ter o Maven configurado nas **Variáveis de Ambiente** do **Windows**.

1.  **Assista ao tutorial de configuração:** [YouTube - Como Baixar Instalar e Configurar as Variaveis de Ambiente Maven](https://youtu.be/mPqp_vmU_w8?si=6Ip7gnupacr89DPV)

2.  Após configurar, abra seu terminal (ou o terminal embutido da IDE) e verifique se o Maven está acessível digitando:
    ```bash
    mvn -v
    ```
    
### 2. Ciclo de Build e Boas Práticas (Clean & Install)

É crucial entender os comandos de ciclo de vida do Maven para garantir que o build do seu projeto esteja sempre limpo e atualizado.

| Comando | O que faz? | Motivo para usar |
| :--- | :--- | :--- |
| **`mvn clean`** | **Limpa a sujeira:** Deleta a pasta `target/` inteira, que é onde ficam os arquivos de build antigos (`.class`, JARs, etc.). | Garante que o projeto não está usando nenhuma versão antiga ou *cacheada* do código. |
| **`mvn install`** | **Compila e Guarda:** Compila (transforma `.java` em `.class`), executa os testes e, o mais importante, **coloca a sua aplicação no repositório Maven local**. | Essencial para que outros módulos ou o próprio `javafx:run` consigam encontrar e usar as classes mais recentes. |


### ⚠️ Aviso Importante:
*Sequência Recomendada para Atualizar o Projeto:*
Sempre que fizer um `pull` (para integrar o código de outra pessoa) ou após realizar **grandes alterações na estrutura/dependências** do projeto, utilize a sequência:

```bash
mvn clean install
```

#### Por que isso é importante?

* O **`clean`** elimina builds antigos que podem causar erros difíceis de rastrear.
* O **`install`** garante que sua aplicação compilada (`.jar`)* vá para o repositório Maven local (`.m2/repository`). Isso é crucial para que o `javafx-maven-plugin` (que roda a view) encontre e utilize a versão mais recente e correta do seu próprio código.

---

## 🎨 Guia de Execução do Frontend (JavaFX)

**Como migramos o projeto para o *Maven* e o projeto agora é modular, a forma de rodar as telas mudou. Não usamos mais o botão "Run" direto na classe *Java (Application)* sem configuração prévia.**

> Abaixo estão as **3 maneiras (1️⃣-2️⃣-3️⃣)** de rodar e testar as telas (`Views`) da aplicação.

### 1️⃣ Opção 1: Rodando pelo Terminal (Linha de Comando)

Esta é a forma mais direta. Graças à configuração dinâmica que fizemos no `pom.xml`, você pode escolher qual tela abrir sem alterar o código.

**Passo a passo:**

1.  Abra o terminal na raiz do projeto (onde está o `pom.xml`).

2.  Para rodar a tela padrão (**Login.view**), digite:
    ```bash
    mvn clean javafx:run
    ```
3.  Para rodar **qualquer outra tela** (ex: Login, CadCadastro, etc), use o comando abaixo trocando apenas o caminho da classe:
    ```bash
    mvn clean javafx:run -D app.run.class=br.com.pi.achadoseperdidos.view.Classe
    ```

> **Nota:** O parâmetro `-D app.run.class` sobrescreve temporariamente a configuração padrão do projeto, permitindo testar telas isoladas.

---

### 2️⃣ Opção 2: Criando Botões de "Run" Personalizados (IDE)

Você pode configurar botões no menu superior do IntelliJ para rodar telas específicas com um clique.

**O Problema:** O IntelliJ bloqueia configurações com o mesmo comando, dizendo *"Configuration already exists"*.
**A Solução:** Usamos um "truque" (`-Dunique.id`) para diferenciar as configurações.

**Passo a passo:**

1.  No topo do IntelliJ, clique na caixa de seleção de execução e vá em **Edit Configurations...**
2.  Clique no **`+`** e selecione **Maven**.
3.  Preencha da seguinte forma para cada tela que quiser criar:

| Campo | O que colocar? (Exemplo para aplicar em TODAS classes da *(.view)* ) |
| :--- | :--- |
| **Name** | `Run View: Cadastro` (Nome amigável para você identificar) |
| **Command line** | `clean javafx:run -D app.run.class=br.com.pi.achadoseperdidos.view.CadCadastro -Dunique.id=Cadastro` |

4.  Clique em **Apply**.
5.  Agora é só selecionar a configuração criada no topo da IDE e clicar no **Play (▶)**.

> **Importante:** O parâmetro `-Dunique.id=NomeDaTela` não afeta o código, serve apenas para o IntelliJ entender que são botões diferentes.

---

### 3️⃣ Opção 3: Ferramenta Maven (Run Configurations)

Após fazer a *(Opção 2)*, você pode rodar utilizando as ferramentas do próprio *Maven*
**Passo a passo:**

1.  Abra a aba **Maven** na lateral direita do IntelliJ.
2.  Vá até a pasta **⚙ Run Configurations** (geralmente representada por ícones de engrenagem *⚙* ou lista de comandos salvos).
3.  Se você seguiu o passo da "Opção 2", todas as suas configurações salvas aparecerão listadas ali.

4.  Basta clicar duas vezes sobre a configuração desejada (ex: `Run View: Login`) para iniciar a aplicação.
