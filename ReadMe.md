# INSTRUÇÕES DE INSTALAÇÃO E EXECUÇÃO

Este documento guia a configuração necessária para que o projeto seja executado corretamente em seu ambiente de desenvolvimento.

---

## 🛠️ Tecnologias e Pré-requisitos

Para que este projeto funcione corretamente, você precisará ter o seguinte configurado:

* **Java Development Kit (JDK) 21:** O projeto requer a **versão 21 do Java**.
* **JavaFX:** Biblioteca utilizada para a interface gráfica (GUI).
* **MySQL Connector/J:** Driver JDBC para a conexão com o banco de dados **MySQL**.

---

### ⚙️ Configuração Inicial do Banco de Dados

É **obrigatório** que o banco de dados e o usuário de conexão sejam criados antes da primeira execução.

1.  **Criação do Esquema e Tabelas:**
    * Localize o arquivo de script SQL na pasta **`database/`** do projeto.
    * Execute este script em seu servidor MySQL para criar o banco de dados (`ap_iguatemi`) e todas as tabelas necessárias.

2.  **Criação do Usuário de Conexão:**
    * Crie um usuário no MySQL com as seguintes credenciais, garantindo que ele tenha permissões de acesso ao esquema `ap_iguatemi`:

| Variável | Valor | Descrição |
| :--- | :--- | :--- |
| **Driver** | `com.mysql.cj.jdbc.Driver` | Driver JDBC a ser utilizado. |
| **URL** | `jdbc:mysql://127.0.0.1:3306/ap_iguatemi` | URL de conexão local (padrão). |
| **Usuário** | `ap_iguatemi_admin` | Usuário de acesso ao banco. |
| **Senha** | `ApIguateMI2025!` | Senha de acesso. |

---

## 🏃 Passo a Passo para Execução

Siga rigorosamente as etapas abaixo para colocar o projeto no ar:

1.  **Clone o Repositório:**
    ```bash
    https://github.com/JoaoVictorSouzaDev/SistemaGerenciamentoIguatemi.git
    ```

2.  **Configuração da Pasta de Recursos (Resources Root):**
    > **IMPORTANTE:** Dentro de sua IDE (ex: IntelliJ IDEA, Eclipse), **marque a pasta `resources`** como um diretório de **Resources Root** (ou Fonte de Recursos). Isso é obrigatório para que o projeto localize arquivos de configuração e arquivos FXML/CSS.

3.  **Configuração das Dependências:**
    * Adicione os arquivos JAR do **JavaFX SDK** e do **MySQL Connector/J** ao `classpath` do seu projeto. (Se estiver usando Maven ou Gradle, verifique se as dependências estão corretamente configuradas).

4.  **Configuração da IDE para JavaFX (VM Options)** 🚨
    * Para que o JavaFX seja executado, é **obrigatório** configurar os argumentos da Máquina Virtual (VM Options) nas **"Edit Configurations"** da sua IDE.
    * Você deve apontar o caminho (`/caminho/para/javafx-sdk/lib`) para onde o seu JavaFX SDK está instalado:

    ```bash
    # Exemplo de VM Options (Substitua o caminho ABSOLUTO)
    --module-path /caminho/para/javafx-sdk/lib --add-modules javafx.controls,javafx.fxml
    ```

5.  **Execução:**
    * Após finalizar todas as configurações acima, compile e execute a classe principal (Main) da sua aplicação.

---