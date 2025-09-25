-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Tempo de geração: 25/09/2025 às 18:45
-- Versão do servidor: 10.4.28-MariaDB
-- Versão do PHP: 8.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Banco de dados: `ap_iguatemi`
--

-- --------------------------------------------------------

--
-- Estrutura para tabela `acessos`
--

CREATE TABLE `acessos` (
  `id` int(11) NOT NULL,
  `nome` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Despejando dados para a tabela `acessos`
--

INSERT INTO `acessos` (`id`, `nome`) VALUES
(1, 'Administrador'),
(2, 'Operador');

-- --------------------------------------------------------

--
-- Estrutura para tabela `categorias`
--

CREATE TABLE `categorias` (
  `id` int(11) NOT NULL,
  `nome` varchar(55) NOT NULL,
  `prazo` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Despejando dados para a tabela `categorias`
--

INSERT INTO `categorias` (`id`, `nome`, `prazo`) VALUES
(1, 'Perecíveis', 3),
(2, 'Roupas', 90),
(3, 'Eletrônicos', 90);

-- --------------------------------------------------------

--
-- Estrutura para tabela `destinatarios`
--

CREATE TABLE `destinatarios` (
  `id` int(11) NOT NULL,
  `nome` varchar(100) NOT NULL,
  `telefone` varchar(20) NOT NULL,
  `cpf` varchar(20) DEFAULT NULL,
  `rg` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Despejando dados para a tabela `destinatarios`
--

INSERT INTO `destinatarios` (`id`, `nome`, `telefone`, `cpf`, `rg`) VALUES
(1, 'João Silva', '(11) 98765-4321', '123.456.789-00', '12.345.678-9'),
(2, 'Maria Oliveira', '(21) 91234-5678', '987.654.321-00', '98.765.432-1'),
(3, 'Carlos Souza', '(31) 99876-5432', '456.789.123-00', '45.678.912-3');

-- --------------------------------------------------------

--
-- Estrutura para tabela `destinos`
--

CREATE TABLE `destinos` (
  `id` int(11) NOT NULL,
  `nome` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Despejando dados para a tabela `destinos`
--

INSERT INTO `destinos` (`id`, `nome`) VALUES
(1, 'Correio'),
(2, 'Descartado'),
(3, 'Doação'),
(4, 'Finalizado'),
(5, 'Incinerado'),
(6, 'Correio'),
(7, 'Inutilizado');

-- --------------------------------------------------------

--
-- Estrutura para tabela `devolucoes`
--

CREATE TABLE `devolucoes` (
  `id` int(11) NOT NULL,
  `dataDevolucao` date NOT NULL,
  `autenticadorAssinatura` varchar(100) NOT NULL,
  `destinatario_id` int(11) DEFAULT NULL,
  `objeto_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Despejando dados para a tabela `devolucoes`
--

INSERT INTO `devolucoes` (`id`, `dataDevolucao`, `autenticadorAssinatura`, `destinatario_id`, `objeto_id`) VALUES
(1, '2025-09-03', '123123', 1, 1),
(2, '2025-09-04', '321321', 2, 2);

-- --------------------------------------------------------

--
-- Estrutura para tabela `eletronicos`
--

CREATE TABLE `eletronicos` (
  `marca` varchar(55) NOT NULL,
  `modelo` varchar(55) NOT NULL,
  `objeto_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Despejando dados para a tabela `eletronicos`
--

INSERT INTO `eletronicos` (`marca`, `modelo`, `objeto_id`) VALUES
('TechBrand', 'Modelo Y', 3),
('TechWorld', 'ProBook X', 8);

-- --------------------------------------------------------

--
-- Estrutura para tabela `fotodevolucoes`
--

CREATE TABLE `fotodevolucoes` (
  `foto_id` int(11) DEFAULT NULL,
  `devolucao_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Despejando dados para a tabela `fotodevolucoes`
--

INSERT INTO `fotodevolucoes` (`foto_id`, `devolucao_id`) VALUES
(3, 1),
(2, 2);

-- --------------------------------------------------------

--
-- Estrutura para tabela `fotoregistros`
--

CREATE TABLE `fotoregistros` (
  `foto_id` int(11) DEFAULT NULL,
  `objeto_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Despejando dados para a tabela `fotoregistros`
--

INSERT INTO `fotoregistros` (`foto_id`, `objeto_id`) VALUES
(1, 1),
(2, 2);

-- --------------------------------------------------------

--
-- Estrutura para tabela `fotos`
--

CREATE TABLE `fotos` (
  `id` int(11) NOT NULL,
  `caminho` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Despejando dados para a tabela `fotos`
--

INSERT INTO `fotos` (`id`, `caminho`) VALUES
(1, '/fotos/objeto1.jpg'),
(2, '/fotos/objeto2.jpg'),
(3, '/fotos/devolucao1.jpg');

-- --------------------------------------------------------

--
-- Estrutura para tabela `funcionarios`
--

CREATE TABLE `funcionarios` (
  `id` int(11) NOT NULL,
  `nome` varchar(100) NOT NULL,
  `email` varchar(100) NOT NULL,
  `senha` varchar(255) NOT NULL,
  `acesso_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Despejando dados para a tabela `funcionarios`
--

INSERT INTO `funcionarios` (`id`, `nome`, `email`, `senha`, `acesso_id`) VALUES
(1, 'Ana Costa', 'ana.costa@empresa.com', 'senha123', 1),
(2, 'Pedro Almeida', 'pedro.almeida@empresa.com', 'senha456', 2),
(3, 'Lucas Mendes', 'lucas.mendes@empresa.com', 'senha789', 2);

-- --------------------------------------------------------

--
-- Estrutura para tabela `locais`
--

CREATE TABLE `locais` (
  `id` int(11) NOT NULL,
  `nome` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Despejando dados para a tabela `locais`
--

INSERT INTO `locais` (`id`, `nome`) VALUES
(1, 'Banheiro feminino piso 1'),
(2, 'Praça de Alimentação 2'),
(3, 'Banheiro masculino piso 2');

-- --------------------------------------------------------

--
-- Estrutura para tabela `objetos`
--

CREATE TABLE `objetos` (
  `id` int(11) NOT NULL,
  `nome` varchar(150) NOT NULL,
  `data_registro` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `lacre` int(11) NOT NULL,
  `condicao` enum('preservado','desgastado','danificado') NOT NULL,
  `observacao` varchar(350) DEFAULT NULL,
  `local_id` int(11) DEFAULT NULL,
  `status_id` int(11) DEFAULT NULL,
  `funcionario_id` int(11) DEFAULT NULL,
  `categoria_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Despejando dados para a tabela `objetos`
--

INSERT INTO `objetos` (`id`, `nome`, `data_registro`, `lacre`, `condicao`, `observacao`, `local_id`, `status_id`, `funcionario_id`, `categoria_id`) VALUES
(1, 'Caixa de Maçãs', '2025-09-01 17:00:00', 1001, 'preservado', 'Caixa em bom estado', 1, 1, 1, 1),
(2, 'Camiseta Azul', '2025-09-01 17:30:00', 1002, 'desgastado', 'Leve desgaste na gola', 2, 1, 2, 2),
(3, 'Smartphone Usado', '2025-09-01 18:00:00', 1003, 'danificado', 'Tela com rachadura', 3, 1, 3, 3),
(4, 'Saco de Laranjas', '2025-09-01 18:30:00', 1004, 'preservado', 'Frutas frescas', 1, 1, 1, 1),
(5, 'Caixa de Leite', '2025-09-01 19:00:00', 1005, 'preservado', 'Embalagem intacta', 2, 1, 2, 1),
(6, 'Calça Jeans', '2025-09-01 19:30:00', 1006, 'preservado', 'Sem manchas', 2, 1, 2, 2),
(7, 'Jaqueta Preta', '2025-09-01 20:00:00', 1007, 'desgastado', 'Zíper com defeito', 3, 1, 3, 2),
(8, 'Notebook Usado', '2025-09-01 20:30:00', 1008, 'danificado', 'Tela com arranhões', 1, 1, 1, 3);

-- --------------------------------------------------------

--
-- Estrutura para tabela `objetosencaminhados`
--

CREATE TABLE `objetosencaminhados` (
  `dataEnvio` date NOT NULL,
  `objeto_id` int(11) DEFAULT NULL,
  `destino_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Despejando dados para a tabela `objetosencaminhados`
--

INSERT INTO `objetosencaminhados` (`dataEnvio`, `objeto_id`, `destino_id`) VALUES
('2025-09-02', 1, 1),
('2025-09-02', 2, 2),
('2025-09-03', 1, 2);

-- --------------------------------------------------------

--
-- Estrutura para tabela `pereciveis`
--

CREATE TABLE `pereciveis` (
  `marca` varchar(55) NOT NULL,
  `objeto_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Despejando dados para a tabela `pereciveis`
--

INSERT INTO `pereciveis` (`marca`, `objeto_id`) VALUES
('Fazenda Verde', 1),
('Frutas do Campo', 4),
('Lacta', 5);

-- --------------------------------------------------------

--
-- Estrutura para tabela `roupas`
--

CREATE TABLE `roupas` (
  `cor` varchar(15) NOT NULL,
  `marca` varchar(55) NOT NULL,
  `tamanho` enum('GG','G','M','P','PP') DEFAULT NULL,
  `objeto_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Despejando dados para a tabela `roupas`
--

INSERT INTO `roupas` (`cor`, `marca`, `tamanho`, `objeto_id`) VALUES
('Azul', 'MarcaX', 'M', 2),
('Azul Escuro', 'DenimCo', 'G', 6),
('Preto', 'FashionZ', 'M', 7);

-- --------------------------------------------------------

--
-- Estrutura para tabela `status`
--

CREATE TABLE `status` (
  `id` int(11) NOT NULL,
  `nome` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Despejando dados para a tabela `status`
--

INSERT INTO `status` (`id`, `nome`) VALUES
(1, 'No prazo'),
(2, 'Destinado'),
(3, 'Devolvido'),
(4, 'Solicitado');

--
-- Índices para tabelas despejadas
--

--
-- Índices de tabela `acessos`
--
ALTER TABLE `acessos`
  ADD PRIMARY KEY (`id`);

--
-- Índices de tabela `categorias`
--
ALTER TABLE `categorias`
  ADD PRIMARY KEY (`id`);

--
-- Índices de tabela `destinatarios`
--
ALTER TABLE `destinatarios`
  ADD PRIMARY KEY (`id`);

--
-- Índices de tabela `destinos`
--
ALTER TABLE `destinos`
  ADD PRIMARY KEY (`id`);

--
-- Índices de tabela `devolucoes`
--
ALTER TABLE `devolucoes`
  ADD PRIMARY KEY (`id`),
  ADD KEY `destinatario_id` (`destinatario_id`),
  ADD KEY `objeto_id` (`objeto_id`);

--
-- Índices de tabela `eletronicos`
--
ALTER TABLE `eletronicos`
  ADD KEY `objeto_id` (`objeto_id`);

--
-- Índices de tabela `fotodevolucoes`
--
ALTER TABLE `fotodevolucoes`
  ADD KEY `foto_id` (`foto_id`),
  ADD KEY `devolucao_id` (`devolucao_id`);

--
-- Índices de tabela `fotoregistros`
--
ALTER TABLE `fotoregistros`
  ADD KEY `foto_id` (`foto_id`),
  ADD KEY `objeto_id` (`objeto_id`);

--
-- Índices de tabela `fotos`
--
ALTER TABLE `fotos`
  ADD PRIMARY KEY (`id`);

--
-- Índices de tabela `funcionarios`
--
ALTER TABLE `funcionarios`
  ADD PRIMARY KEY (`id`),
  ADD KEY `acesso_id` (`acesso_id`);

--
-- Índices de tabela `locais`
--
ALTER TABLE `locais`
  ADD PRIMARY KEY (`id`);

--
-- Índices de tabela `objetos`
--
ALTER TABLE `objetos`
  ADD PRIMARY KEY (`id`),
  ADD KEY `local_id` (`local_id`),
  ADD KEY `status_id` (`status_id`),
  ADD KEY `funcionario_id` (`funcionario_id`),
  ADD KEY `categoria_id` (`categoria_id`);

--
-- Índices de tabela `objetosencaminhados`
--
ALTER TABLE `objetosencaminhados`
  ADD KEY `objeto_id` (`objeto_id`),
  ADD KEY `destino_id` (`destino_id`);

--
-- Índices de tabela `pereciveis`
--
ALTER TABLE `pereciveis`
  ADD KEY `objeto_id` (`objeto_id`);

--
-- Índices de tabela `roupas`
--
ALTER TABLE `roupas`
  ADD KEY `objeto_id` (`objeto_id`);

--
-- Índices de tabela `status`
--
ALTER TABLE `status`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT para tabelas despejadas
--

--
-- AUTO_INCREMENT de tabela `acessos`
--
ALTER TABLE `acessos`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de tabela `categorias`
--
ALTER TABLE `categorias`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT de tabela `destinatarios`
--
ALTER TABLE `destinatarios`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT de tabela `destinos`
--
ALTER TABLE `destinos`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT de tabela `devolucoes`
--
ALTER TABLE `devolucoes`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de tabela `fotos`
--
ALTER TABLE `fotos`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT de tabela `funcionarios`
--
ALTER TABLE `funcionarios`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT de tabela `locais`
--
ALTER TABLE `locais`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT de tabela `objetos`
--
ALTER TABLE `objetos`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT de tabela `status`
--
ALTER TABLE `status`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- Restrições para tabelas despejadas
--

--
-- Restrições para tabelas `devolucoes`
--
ALTER TABLE `devolucoes`
  ADD CONSTRAINT `devolucoes_ibfk_1` FOREIGN KEY (`destinatario_id`) REFERENCES `destinatarios` (`id`),
  ADD CONSTRAINT `devolucoes_ibfk_2` FOREIGN KEY (`objeto_id`) REFERENCES `objetos` (`id`);

--
-- Restrições para tabelas `eletronicos`
--
ALTER TABLE `eletronicos`
  ADD CONSTRAINT `eletronicos_ibfk_1` FOREIGN KEY (`objeto_id`) REFERENCES `objetos` (`id`);

--
-- Restrições para tabelas `fotodevolucoes`
--
ALTER TABLE `fotodevolucoes`
  ADD CONSTRAINT `fotodevolucoes_ibfk_1` FOREIGN KEY (`foto_id`) REFERENCES `fotos` (`id`),
  ADD CONSTRAINT `fotodevolucoes_ibfk_2` FOREIGN KEY (`devolucao_id`) REFERENCES `devolucoes` (`id`);

--
-- Restrições para tabelas `fotoregistros`
--
ALTER TABLE `fotoregistros`
  ADD CONSTRAINT `fotoregistros_ibfk_1` FOREIGN KEY (`foto_id`) REFERENCES `fotos` (`id`),
  ADD CONSTRAINT `fotoregistros_ibfk_2` FOREIGN KEY (`objeto_id`) REFERENCES `objetos` (`id`);

--
-- Restrições para tabelas `funcionarios`
--
ALTER TABLE `funcionarios`
  ADD CONSTRAINT `funcionarios_ibfk_1` FOREIGN KEY (`acesso_id`) REFERENCES `acessos` (`id`);

--
-- Restrições para tabelas `objetos`
--
ALTER TABLE `objetos`
  ADD CONSTRAINT `objetos_ibfk_1` FOREIGN KEY (`local_id`) REFERENCES `locais` (`id`),
  ADD CONSTRAINT `objetos_ibfk_2` FOREIGN KEY (`status_id`) REFERENCES `status` (`id`),
  ADD CONSTRAINT `objetos_ibfk_3` FOREIGN KEY (`funcionario_id`) REFERENCES `funcionarios` (`id`),
  ADD CONSTRAINT `objetos_ibfk_4` FOREIGN KEY (`categoria_id`) REFERENCES `categorias` (`id`);

--
-- Restrições para tabelas `objetosencaminhados`
--
ALTER TABLE `objetosencaminhados`
  ADD CONSTRAINT `objetosencaminhados_ibfk_1` FOREIGN KEY (`objeto_id`) REFERENCES `objetos` (`id`),
  ADD CONSTRAINT `objetosencaminhados_ibfk_2` FOREIGN KEY (`destino_id`) REFERENCES `destinos` (`id`);

--
-- Restrições para tabelas `pereciveis`
--
ALTER TABLE `pereciveis`
  ADD CONSTRAINT `pereciveis_ibfk_1` FOREIGN KEY (`objeto_id`) REFERENCES `objetos` (`id`);

--
-- Restrições para tabelas `roupas`
--
ALTER TABLE `roupas`
  ADD CONSTRAINT `roupas_ibfk_1` FOREIGN KEY (`objeto_id`) REFERENCES `objetos` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
