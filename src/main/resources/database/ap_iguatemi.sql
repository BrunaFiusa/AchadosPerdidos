-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Tempo de geração: 29/10/2025 às 20:36
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
-- Banco de dados: `achadosperdidos`
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
(3, 'Utensílios', 90),
(4, 'Livros', 90),
(5, 'Brinquedos', 90),
(6, 'Documentos', 30),
(7, 'Calçados', 90),
(8, 'Diversos', 90),
(9, 'Acessórios', 90),
(10, 'Material Escolar', 90),
(11, 'Joias', 90),
(12, 'Chaves', 60),
(13, 'Carteiras', 90),
(14, 'Bolsas', 90),
(15, 'Eletrônicos', 90);

-- --------------------------------------------------------

--
-- Estrutura para tabela `destinatarios`
--

CREATE TABLE `destinatarios` (
  `id` int(11) NOT NULL,
  `nome` varchar(100) NOT NULL,
  `telefone` varchar(20) NOT NULL,
  `cpf` varchar(20) DEFAULT NULL,
  `rg` varchar(20) DEFAULT NULL,
  `ativado` tinyint(4) DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Despejando dados para a tabela `destinatarios`
--

INSERT INTO `destinatarios` (`id`, `nome`, `telefone`, `cpf`, `rg`, `ativado`) VALUES
(1, 'João Silva', '(11) 98765-4321', '123.456.789-00', '12.345.678-9', 1),
(2, 'Maria Oliveira', '(21) 91234-5678', '987.654.321-00', '98.765.432-1', 1),
(3, 'Carlos Souza', '(31) 99876-5432', '456.789.123-00', '45.678.912-3', 1),
(4, 'Vinicius Henrique', '(35) 9445-9949', '336.314.767-00', '60.194.456-9', 1),
(5, 'Juliana Oliveira', '(21) 9896-6166', '828.252.634-00', '26.844.081-9', 1),
(6, 'Ana Castro', '(18) 9001-5946', '434.778.595-00', '60.074.601-9', 1),
(7, 'Fernanda Barbosa', '(70) 9508-4884', '870.561.223-00', '41.428.239-9', 1),
(8, 'Vinicius Henrique', '(40) 9958-1223', '151.747.874-00', '98.698.257-9', 1),
(9, 'Juliana Almeida', '(89) 9669-6455', '926.870.754-00', '28.846.399-9', 1),
(10, 'Pedro Gonçalves', '(83) 9062-9009', '940.264.981-00', '04.636.464-9', 1),
(11, 'Carlos Almeida', '(43) 9961-1177', '502.110.381-00', '91.683.868-9', 1),
(12, 'Beatriz Dias', '(79) 9999-5504', '784.280.290-00', '34.265.787-9', 1),
(13, 'Vinicius Gonçalves', '(28) 9167-7482', '938.897.027-00', '60.774.732-9', 1),
(14, 'Lucas Rodrigues', '(48) 9177-2818', '121.009.141-00', '73.268.690-9', 1),
(15, 'Camila Barbosa', '(83) 9941-5243', '746.531.795-00', '87.488.758-9', 1);

-- --------------------------------------------------------

--
-- Estrutura para tabela `destinos`
--

CREATE TABLE `destinos` (
  `id` int(11) NOT NULL,
  `nome` varchar(50) NOT NULL,
  `ativado` tinyint(4) NOT NULL DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Despejando dados para a tabela `destinos`
--

INSERT INTO `destinos` (`id`, `nome`, `ativado`) VALUES
(1, 'Correio', 1),
(2, 'Destinação', 1),
(3, 'Doação', 1),
(4, 'Finalizado', 1),
(5, 'Incinerado', 1),
(6, 'Inutilizado', 1),
(7, 'Descartado', 1);

-- --------------------------------------------------------

--
-- Estrutura para tabela `devolucoes`
--

CREATE TABLE `devolucoes` (
  `id` int(11) NOT NULL,
  `dataDevolucao` date NOT NULL,
  `autenticadorAssinatura` varchar(100) NOT NULL,
  `destinatario_id` int(11) DEFAULT NULL,
  `objeto_id` int(11) DEFAULT NULL,
  `ativado` tinyint(4) NOT NULL DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Despejando dados para a tabela `devolucoes`
--

INSERT INTO `devolucoes` (`id`, `dataDevolucao`, `autenticadorAssinatura`, `destinatario_id`, `objeto_id`, `ativado`) VALUES
(1, '2025-01-10', 'auth001', 1, 1, 1),
(2, '2025-01-11', 'auth002', 2, 2, 1),
(3, '2025-01-12', 'auth003', 3, 3, 1),
(4, '2025-01-13', 'auth004', 4, 4, 1),
(5, '2025-01-14', 'auth005', 5, 5, 1),
(6, '2025-01-15', 'auth006', 6, 6, 1),
(7, '2025-01-16', 'auth007', 7, 7, 1),
(8, '2025-01-17', 'auth008', 8, 8, 1),
(9, '2025-01-18', 'auth009', 9, 9, 1),
(10, '2025-01-19', 'auth010', 10, 10, 1),
(11, '2025-01-20', 'auth011', 11, 11, 1),
(12, '2025-01-21', 'auth012', 12, 12, 1),
(13, '2025-01-22', 'auth013', 13, 13, 1),
(14, '2025-01-23', 'auth014', 14, 14, 1),
(15, '2025-01-24', 'auth015', 15, 15, 1),
(16, '2025-01-25', 'auth016', 1, 16, 1),
(17, '2025-01-26', 'auth017', 2, 17, 1),
(18, '2025-01-27', 'auth018', 3, 18, 1),
(19, '2025-01-28', 'auth019', 4, 19, 1),
(20, '2025-01-29', 'auth020', 5, 20, 1);

-- --------------------------------------------------------

--
-- Estrutura para tabela `eletronicos`
--

CREATE TABLE `eletronicos` (
  `modelo` varchar(55) NOT NULL,
  `objeto_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Despejando dados para a tabela `eletronicos`
--

INSERT INTO `eletronicos` (`modelo`, `objeto_id`) VALUES
('A10', 21),
('Inspiron 15', 22),
('Tab M10', 23),
('Tune 510', 24),
('Bip U', 25),
('Go 3', 26),
('M280', 27),
('PowerCore 10000', 28),
('WH-1000XM4', 29),
('Paperwhite 10ª Geração', 30),
('EOS T6', 31),
('DualShock 4', 32),
('Aspire 3', 33),
('EP-TA800', 34),
('Zeus X', 35),
('iPhone 11', 36),
('F91W', 37),
('GO Bluetooth', 38),
('Ideapad 3', 39),
('iPad Mini', 40);

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
(1, 1),
(2, 2),
(3, 3),
(4, 4),
(5, 5),
(6, 6),
(7, 7),
(8, 8),
(9, 9),
(10, 10),
(11, 11),
(12, 12),
(13, 13),
(14, 14),
(15, 15),
(1, 16),
(2, 17),
(3, 18),
(4, 19),
(5, 20);

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
(2, 2),
(3, 3),
(4, 4),
(5, 5),
(6, 6),
(7, 7),
(8, 8),
(9, 9),
(10, 10),
(11, 11),
(12, 12),
(13, 13),
(14, 14),
(15, 15),
(1, 16),
(2, 17),
(3, 18),
(4, 19),
(5, 20),
(6, 21),
(7, 22),
(8, 23),
(9, 24),
(10, 25),
(11, 26),
(12, 27),
(13, 28),
(14, 29),
(15, 30),
(1, 31),
(2, 32),
(3, 33),
(4, 34),
(5, 35),
(6, 36),
(7, 37),
(8, 38),
(9, 39),
(10, 40),
(11, 41),
(12, 42),
(13, 43),
(14, 44),
(15, 45),
(1, 46),
(2, 47),
(3, 48),
(4, 49),
(5, 50),
(6, 51),
(7, 52),
(8, 53),
(9, 54),
(10, 55),
(11, 56),
(12, 57),
(13, 58),
(14, 59),
(15, 60);

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
(3, '/fotos/devolucao1.jpg'),
(4, '/fotos/objeto88.jpg'),
(5, '/fotos/objeto354.jpg'),
(6, '/fotos/objeto600.jpg'),
(7, '/fotos/objeto26.jpg'),
(8, '/fotos/objeto654.jpg'),
(9, '/fotos/objeto775.jpg'),
(10, '/fotos/objeto780.jpg'),
(11, '/fotos/objeto651.jpg'),
(12, '/fotos/objeto393.jpg'),
(13, '/fotos/objeto24.jpg'),
(14, '/fotos/objeto889.jpg'),
(15, '/fotos/objeto876.jpg');

-- --------------------------------------------------------

--
-- Estrutura para tabela `funcionarios`
--

CREATE TABLE `funcionarios` (
  `id` int(11) NOT NULL,
  `nome` varchar(100) NOT NULL,
  `email` varchar(100) NOT NULL,
  `senha` varchar(255) NOT NULL,
  `acesso_id` int(11) DEFAULT NULL,
  `ativado` tinyint(4) NOT NULL DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Despejando dados para a tabela `funcionarios`
--

INSERT INTO `funcionarios` (`id`, `nome`, `email`, `senha`, `acesso_id`, `ativado`) VALUES
(1, 'Ana Costa', 'ana.costa@empresa.com', 'senha123', 1, 1),
(2, 'Pedro Almeida', 'pedro.almeida@empresa.com', 'senha456', 2, 1),
(3, 'Lucas Mendes', 'lucas.mendes@empresa.com', 'senha789', 2, 1),
(4, 'Fernanda Rodrigues', 'fernanda.rodrigues@empresa.com', 'senha831', 2, 1),
(5, 'Isabela Castro', 'isabela.castro@empresa.com', 'senha732', 2, 1),
(6, 'Isabela Gonçalves', 'isabela.gonçalves@empresa.com', 'senha422', 2, 1),
(7, 'Maria Lima', 'maria.lima@empresa.com', 'senha311', 2, 1),
(8, 'Fernanda Araujo', 'fernanda.araujo@empresa.com', 'senha837', 2, 1),
(9, 'Camila Carvalho', 'camila.carvalho@empresa.com', 'senha828', 2, 1),
(10, 'João Lima', 'joão.lima@empresa.com', 'senha770', 2, 1),
(11, 'Vinicius Castro', 'vinicius.castro@empresa.com', 'senha630', 2, 1),
(12, 'Fernanda Barbosa', 'fernanda.barbosa@empresa.com', 'senha674', 1, 1),
(13, 'Fernanda Gomes', 'fernanda.gomes@empresa.com', 'senha116', 2, 1),
(14, 'Maria Barbosa', 'maria.barbosa@empresa.com', 'senha539', 2, 1),
(15, 'Beatriz Gomes', 'beatriz.gomes@empresa.com', 'senha950', 2, 1);

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
(3, 'Banheiro masculino piso 2'),
(4, 'Escada rolante'),
(5, 'Corredor principal'),
(6, 'Estacionamento'),
(7, 'Sala de espera'),
(8, 'Estacionamento'),
(9, 'Loja 2'),
(10, 'Elevador'),
(11, 'Banheiro masculino piso 2'),
(12, 'Banheiro feminino piso 1'),
(13, 'Sala de espera'),
(14, 'Corredor principal'),
(15, 'Banheiro masculino piso 2');

-- --------------------------------------------------------

--
-- Estrutura para tabela `objetos`
--

CREATE TABLE `objetos` (
  `id` int(11) NOT NULL,
  `nome` varchar(150) NOT NULL,
  `marca` varchar(150) NOT NULL,
  `data_registro` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `lacre` int(11) NOT NULL,
  `condicao` enum('preservado','desgastado','danificado') NOT NULL,
  `observacao` varchar(350) DEFAULT NULL,
  `local_id` int(11) DEFAULT NULL,
  `status_id` int(11) DEFAULT NULL,
  `funcionario_id` int(11) DEFAULT NULL,
  `categoria_id` int(11) DEFAULT NULL,
  `ativado` tinyint(4) NOT NULL DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Despejando dados para a tabela `objetos`
--

INSERT INTO `objetos` (`id`, `nome`, `marca`, `data_registro`, `lacre`, `condicao`, `observacao`, `local_id`, `status_id`, `funcionario_id`, `categoria_id`, `ativado`) VALUES
(1, 'Jaqueta Jeans', 'Levi’s', '2025-10-14 19:57:32', 1001, 'preservado', 'Sem manchas visíveis', 1, 1, 2, 2, 1),
(2, 'Camiseta Preta', 'Hering', '2025-10-14 19:57:32', 1002, 'preservado', 'Etiqueta cortada', 2, 1, 3, 2, 1),
(3, 'Calça Jeans', 'C&A', '2025-10-14 19:57:32', 1003, 'desgastado', 'Pequeno rasgo na barra', 3, 2, 4, 2, 1),
(4, 'Blusa Vermelha', 'Renner', '2025-10-14 19:57:32', 1004, 'preservado', 'Ligeiro desbotamento', 4, 1, 5, 2, 1),
(5, 'Casaco de Moletom', 'Adidas', '2025-10-14 19:57:32', 1005, 'preservado', 'Zíper intacto', 5, 1, 6, 2, 1),
(6, 'Camisa Social', 'Zara', '2025-10-14 19:57:32', 1006, 'preservado', 'Leve amassado', 6, 1, 7, 2, 1),
(7, 'Saia Jeans', 'Riachuelo', '2025-10-14 19:57:32', 1007, 'preservado', 'Nova', 7, 1, 8, 2, 1),
(8, 'Short Preto', 'Nike', '2025-10-14 19:57:32', 1008, 'preservado', 'Novo', 8, 1, 9, 2, 1),
(9, 'Calça Legging', 'Reebok', '2025-10-14 19:57:32', 1009, 'preservado', 'Pouco uso', 9, 1, 10, 2, 1),
(10, 'Vestido Floral', 'Marisa', '2025-10-14 19:57:32', 1010, 'preservado', 'Bom estado', 10, 1, 11, 2, 1),
(11, 'Camiseta Azul', 'Hering', '2025-10-14 19:57:32', 1011, 'preservado', 'Sem uso', 11, 1, 12, 2, 1),
(12, 'Blusa de Tricô', 'YouCom', '2025-10-14 19:57:32', 1012, 'preservado', 'Limpa e dobrada', 12, 1, 13, 2, 1),
(13, 'Calça Moletom', 'Puma', '2025-10-14 19:57:32', 1013, 'desgastado', 'Sinais leves de uso', 13, 2, 14, 2, 1),
(14, 'Camisa Polo', 'Lacoste', '2025-10-14 19:57:32', 1014, 'preservado', 'Pequeno bordado no peito', 14, 1, 15, 2, 1),
(15, 'Jaqueta de Couro', 'Riachuelo', '2025-10-14 19:57:32', 1015, 'preservado', 'Zíper funcional', 15, 1, 8, 2, 1),
(16, 'Blusa Rosa', 'Hering', '2025-10-14 19:57:32', 1016, 'preservado', 'Limpa', 5, 1, 9, 2, 1),
(17, 'Casaco Preto', 'Renner', '2025-10-14 19:57:32', 1017, 'preservado', 'Pouco uso', 3, 1, 10, 2, 1),
(18, 'Calça Sarja', 'Zara', '2025-10-14 19:57:32', 1018, 'preservado', 'Nova', 2, 1, 11, 2, 1),
(19, 'Short Jeans', 'C&A', '2025-10-14 19:57:32', 1019, 'preservado', 'Com cinto', 4, 1, 12, 2, 1),
(20, 'Camisa Xadrez', 'Hering', '2025-10-14 19:57:32', 1020, 'preservado', 'Sem defeitos', 6, 1, 13, 2, 1),
(21, 'Celular Samsung Galaxy A10', 'Samsung', '2025-10-14 19:57:32', 2001, 'desgastado', 'Tela com leve trinca', 1, 2, 1, 15, 1),
(22, 'Notebook Dell Inspiron', 'Dell', '2025-10-14 19:57:32', 2002, 'preservado', 'Com carregador', 2, 1, 2, 15, 1),
(23, 'Tablet Lenovo Tab M10', 'Lenovo', '2025-10-14 19:57:32', 2003, 'preservado', 'Bom estado', 3, 1, 3, 15, 1),
(24, 'Fone Bluetooth JBL', 'JBL', '2025-10-14 19:57:32', 2004, 'preservado', 'Com caixa', 4, 1, 4, 15, 1),
(25, 'Smartwatch Amazfit', 'Xiaomi', '2025-10-14 19:57:32', 2005, 'preservado', 'Sem riscos', 5, 1, 5, 15, 1),
(26, 'Caixa de Som JBL Go', 'JBL', '2025-10-14 19:57:32', 2006, 'preservado', 'Novo', 6, 1, 6, 15, 1),
(27, 'Mouse Sem Fio', 'Logitech', '2025-10-14 19:57:32', 2007, 'preservado', 'Funcional', 7, 1, 7, 15, 1),
(28, 'Power Bank', 'Anker', '2025-10-14 19:57:32', 2008, 'preservado', 'Nova', 8, 1, 8, 15, 1),
(29, 'Fone de Ouvido', 'Sony', '2025-10-14 19:57:32', 2009, 'preservado', 'Em caixa', 9, 1, 9, 15, 1),
(30, 'Kindle Paperwhite', 'Amazon', '2025-10-14 19:57:32', 2010, 'preservado', 'Com capa', 10, 1, 10, 15, 1),
(31, 'Câmera Canon T6', 'Canon', '2025-10-14 19:57:32', 2011, 'preservado', 'Com lente 18-55mm', 11, 1, 11, 15, 1),
(32, 'Controle de PS4', 'Sony', '2025-10-14 19:57:32', 2012, 'preservado', 'Novo', 12, 1, 12, 15, 1),
(33, 'Notebook Acer Aspire', 'Acer', '2025-10-14 19:57:32', 2013, 'desgastado', 'Carcaça riscada', 13, 2, 13, 15, 1),
(34, 'Carregador USB-C', 'Samsung', '2025-10-14 19:57:32', 2014, 'preservado', 'Funcionando', 14, 1, 14, 15, 1),
(35, 'Headset Gamer', 'Redragon', '2025-10-14 19:57:32', 2015, 'preservado', 'Pouco uso', 15, 1, 15, 15, 1),
(36, 'Smartphone iPhone 11', 'Apple', '2025-10-14 19:57:32', 2016, 'preservado', 'Com película', 9, 1, 9, 15, 1),
(37, 'Relógio Digital', 'Casio', '2025-10-14 19:57:32', 2017, 'preservado', 'Funciona bem', 10, 1, 10, 15, 1),
(38, 'Caixa Bluetooth', 'Philco', '2025-10-14 19:57:32', 2018, 'preservado', 'Sem danos', 13, 1, 13, 15, 1),
(39, 'Notebook Lenovo Ideapad', 'Lenovo', '2025-10-14 19:57:32', 2019, 'preservado', 'Pouco uso', 5, 1, 5, 15, 1),
(40, 'Tablet iPad Mini', 'Apple', '2025-10-14 19:57:32', 2020, 'preservado', 'Sem riscos', 8, 1, 8, 15, 1),
(41, 'Carteira de Couro', 'Sem marca', '2025-10-14 20:01:31', 3001, 'preservado', 'Com documentos dentro', 1, 1, 1, 13, 1),
(42, 'Livro Dom Casmurro', 'Editora Ática', '2025-10-14 20:01:31', 3002, 'preservado', 'Sem anotações', 2, 1, 2, 4, 1),
(43, 'Chave de Carro', 'Volkswagen', '2025-10-14 20:01:31', 3003, 'preservado', 'Chave reserva', 3, 1, 3, 12, 1),
(44, 'Brinquedo Carrinho Azul', 'Hot Wheels', '2025-10-14 20:01:31', 3004, 'preservado', 'Novo', 4, 1, 4, 5, 1),
(45, 'Óculos de Sol', 'Ray-Ban', '2025-10-14 20:01:31', 3005, 'preservado', 'Com estojo', 5, 1, 5, 9, 1),
(46, 'Garrafa Térmica', 'Stanley', '2025-10-14 20:01:31', 3006, 'preservado', 'Nova', 6, 1, 6, 3, 1),
(47, 'Livro O Pequeno Príncipe', 'HarperCollins', '2025-10-14 20:01:31', 3007, 'preservado', 'Nova edição', 7, 1, 7, 4, 1),
(48, 'Carteira Preta', 'Sem marca', '2025-10-14 20:01:31', 3008, 'preservado', 'Vazia', 8, 1, 8, 13, 1),
(49, 'Chaveiro Vermelho', 'Fiat', '2025-10-14 20:01:31', 3009, 'preservado', 'Com etiqueta', 9, 1, 9, 12, 1),
(50, 'Garrafa de Água', 'Camelbak', '2025-10-14 20:01:31', 3010, 'preservado', 'Pouco uso', 10, 1, 10, 3, 1),
(51, 'Livro A Metamorfose', 'Companhia das Letras', '2025-10-14 20:01:31', 3011, 'preservado', 'Sem rasgos', 11, 1, 11, 4, 1),
(52, 'Mochila Preta', 'Nike', '2025-10-14 20:01:31', 3012, 'preservado', 'Usada', 12, 1, 12, 14, 1),
(53, 'Caneta Azul', 'Bic', '2025-10-14 20:01:31', 3013, 'preservado', 'Nova', 13, 1, 13, 10, 1),
(54, 'Chave de Casa', 'Sem marca', '2025-10-14 20:01:31', 3014, 'preservado', 'Com chaveiro', 14, 1, 14, 12, 1),
(55, 'Guarda-chuva Preto', 'Sem marca', '2025-10-14 20:01:31', 3015, 'preservado', 'Fechado', 15, 1, 15, 3, 1),
(56, 'Livro Harry Potter', 'Rocco', '2025-10-14 20:01:31', 3016, 'preservado', 'Capa dura', 10, 1, 10, 4, 1),
(57, 'Caneta Azul', 'Pilot', '2025-10-14 20:01:31', 3017, 'preservado', 'Nova', 9, 1, 9, 10, 1),
(58, 'Pulseira Dourada', 'Vivara', '2025-10-14 20:01:31', 3018, 'preservado', 'Em estojo', 11, 1, 11, 11, 1),
(59, 'Boné Preto', 'Nike', '2025-10-14 20:01:31', 3019, 'preservado', 'Pouco uso', 12, 1, 12, 2, 1),
(60, 'Caderno Azul', 'Tilibra', '2025-10-14 20:01:31', 3020, 'preservado', 'Sem uso', 13, 1, 13, 10, 1);

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
('2025-02-01', 21, 1),
('2025-02-02', 22, 2),
('2025-02-03', 23, 3),
('2025-02-04', 24, 4),
('2025-02-05', 25, 5),
('2025-02-06', 26, 6),
('2025-02-07', 27, 7),
('2025-02-08', 28, 1),
('2025-02-09', 29, 2),
('2025-02-10', 30, 3),
('2025-02-11', 31, 4),
('2025-02-12', 32, 5),
('2025-02-13', 33, 6),
('2025-02-14', 34, 7),
('2025-02-15', 35, 1),
('2025-02-16', 36, 2),
('2025-02-17', 37, 3),
('2025-02-18', 38, 4),
('2025-02-19', 39, 5),
('2025-02-20', 40, 6);

-- --------------------------------------------------------

--
-- Estrutura para tabela `roupas`
--

CREATE TABLE `roupas` (
  `cor` varchar(15) NOT NULL,
  `tamanho` enum('GG','G','M','P','PP') DEFAULT NULL,
  `objeto_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Despejando dados para a tabela `roupas`
--

INSERT INTO `roupas` (`cor`, `tamanho`, `objeto_id`) VALUES
('Azul', 'M', 1),
('Preta', 'G', 2),
('Azul Escuro', 'G', 3),
('Vermelha', 'M', 4),
('Cinza', 'G', 5),
('Branca', 'M', 6),
('Jeans', 'M', 7),
('Preto', 'G', 8),
('Cinza', 'M', 9),
('Floral', 'P', 10),
('Azul', 'G', 11),
('Bege', 'M', 12),
('Cinza', 'G', 13),
('Verde', 'M', 14),
('Preta', 'G', 15),
('Rosa', 'P', 16),
('Preta', 'G', 17),
('Caqui', 'M', 18),
('Jeans', 'M', 19),
('Xadrez', 'G', 20);

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
(1, 'Devolvido'),
(2, 'Finalizado'),
(3, 'Vencido');

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
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- AUTO_INCREMENT de tabela `destinatarios`
--
ALTER TABLE `destinatarios`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- AUTO_INCREMENT de tabela `destinos`
--
ALTER TABLE `destinos`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT de tabela `devolucoes`
--
ALTER TABLE `devolucoes`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=21;

--
-- AUTO_INCREMENT de tabela `fotos`
--
ALTER TABLE `fotos`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- AUTO_INCREMENT de tabela `funcionarios`
--
ALTER TABLE `funcionarios`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- AUTO_INCREMENT de tabela `locais`
--
ALTER TABLE `locais`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- AUTO_INCREMENT de tabela `objetos`
--
ALTER TABLE `objetos`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=61;

--
-- AUTO_INCREMENT de tabela `status`
--
ALTER TABLE `status`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

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
-- Restrições para tabelas `roupas`
--
ALTER TABLE `roupas`
  ADD CONSTRAINT `roupas_ibfk_1` FOREIGN KEY (`objeto_id`) REFERENCES `objetos` (`id`);

ALTER TABLE objetos
CHANGE data_registro dataReg VARCHAR(20);

ALTER TABLE categorias
ADD COLUMN ativado BOOLEAN NOT NULL;

ALTER TABLE devolucoes
CHANGE autenticadorAssinatura autenticadorAss VARCHAR(20);

ALTER TABLE status 
ADD COLUMN ativado TINYINT(1) NOT NULL DEFAULT 1;

UPDATE status SET ativado = 1 WHERE ativado IS NULL;

ALTER TABLE locais 
ADD COLUMN ativado TINYINT(1) NOT NULL DEFAULT 1;

UPDATE locais SET ativado = 1 WHERE ativado IS NULL;

SET FOREIGN_KEY_CHECKS = 0;  

RENAME TABLE 
    acessos TO Acesso,
    categorias TO Categoria,
    destinos TO Destino,
    fotos TO Foto,
    locais TO Local;
    
RENAME TABLE 
    destinatarios TO Destinatario,
    funcionarios TO Funcionario;

RENAME TABLE 
    devolucoes TO Devolucao,
    objetos TO Objeto;

RENAME TABLE 
    eletronicos TO Eletronico,
    fotodevolucoes TO DevolucaoFoto,
    fotoregistros TO RegistroFoto,
    objetosencaminhados TO ObjetoEncaminhado,
    roupas TO Roupa;

SET FOREIGN_KEY_CHECKS = 1;  

COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
