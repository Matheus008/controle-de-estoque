-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Tempo de geração: 17/09/2025 às 01:15
-- Versão do servidor: 10.4.32-MariaDB
-- Versão do PHP: 8.0.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Banco de dados: `estoque_db`
--

-- --------------------------------------------------------

--
-- Estrutura para tabela `cliente_tb`
--

CREATE TABLE `cliente_tb` (
  `id` int(11) NOT NULL,
  `cpf_ou_cnpj` varchar(255) DEFAULT NULL,
  `nome_cliente` varchar(255) DEFAULT NULL,
  `tipo_cliente` enum('FISICA','JURIDICA') DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Despejando dados para a tabela `cliente_tb`
--

INSERT INTO `cliente_tb` (`id`, `cpf_ou_cnpj`, `nome_cliente`, `tipo_cliente`) VALUES
(1, '789.456.123-00', 'Jáo', 'FISICA'),
(2, '78.945.612/3000-00', 'Claudinho', 'JURIDICA'),
(5, '78.945.612/3000-00', 'Claudinho', 'JURIDICA'),
(7, '78.945.612/3000-00', 'Claudinho', 'JURIDICA');

-- --------------------------------------------------------

--
-- Estrutura para tabela `config_estoque_tb`
--

CREATE TABLE `config_estoque_tb` (
  `id` bigint(20) NOT NULL,
  `estoque_maximo` int(11) DEFAULT NULL,
  `estoque_minimo` int(11) DEFAULT NULL,
  `status` tinyint(4) DEFAULT NULL,
  `produto_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Despejando dados para a tabela `config_estoque_tb`
--

INSERT INTO `config_estoque_tb` (`id`, `estoque_maximo`, `estoque_minimo`, `status`, `produto_id`) VALUES
(1, 30, 10, 2, 8),
(2, 30, 10, 0, 1),
(3, 15, 10, 1, 3),
(4, 15, 1, 1, 2);

-- --------------------------------------------------------

--
-- Estrutura para tabela `config_estoque_tb_seq`
--

CREATE TABLE `config_estoque_tb_seq` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Despejando dados para a tabela `config_estoque_tb_seq`
--

INSERT INTO `config_estoque_tb_seq` (`next_val`) VALUES
(101);

-- --------------------------------------------------------

--
-- Estrutura para tabela `fornecedor_tb`
--

CREATE TABLE `fornecedor_tb` (
  `id` int(11) NOT NULL,
  `cpf_ou_cnpj` varchar(255) DEFAULT NULL,
  `nome_fornecedor` varchar(255) DEFAULT NULL,
  `tipo_fornecedor` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Despejando dados para a tabela `fornecedor_tb`
--

INSERT INTO `fornecedor_tb` (`id`, `cpf_ou_cnpj`, `nome_fornecedor`, `tipo_fornecedor`) VALUES
(1, '789.456.123-00', 'Xinguilingui', 'FISICA'),
(3, '78.945.612/3000-00', 'Xinguilingui', 'JURIDICA');

-- --------------------------------------------------------

--
-- Estrutura para tabela `movimentacao_tb`
--

CREATE TABLE `movimentacao_tb` (
  `id` int(11) NOT NULL,
  `data_hora` datetime(6) DEFAULT NULL,
  `descricao` varchar(255) DEFAULT NULL,
  `quantidade` int(11) DEFAULT NULL,
  `tipo_de_movimentacao` enum('ENTRADA','SAIDA') DEFAULT NULL,
  `produto_id` int(11) DEFAULT NULL,
  `usuario_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Despejando dados para a tabela `movimentacao_tb`
--

INSERT INTO `movimentacao_tb` (`id`, `data_hora`, `descricao`, `quantidade`, `tipo_de_movimentacao`, `produto_id`, `usuario_id`) VALUES
(1, '2025-08-23 19:30:47.000000', '', 20, 'ENTRADA', 8, 1),
(2, '2025-08-23 19:33:26.000000', '', 1, 'ENTRADA', 8, 1),
(3, '2025-08-23 19:34:58.000000', 'Venda', 1, 'SAIDA', 8, 1),
(4, '2025-08-25 23:36:21.000000', 'Venda', 5, 'ENTRADA', 2, 1),
(5, '2025-08-27 00:17:37.000000', 'Venda realizada para o cliente: Jáo', 2, 'SAIDA', 2, 1),
(6, '2025-08-29 01:19:01.000000', 'compra', 10, 'ENTRADA', 8, 1),
(7, '2025-08-29 01:38:13.000000', 'Venda realizada para o cliente: Jáo', 5, 'SAIDA', 8, 1),
(8, '2025-08-29 14:35:33.000000', 'compra', 10, 'ENTRADA', 8, 3),
(9, '2025-08-30 19:33:55.000000', 'compra', 10, 'ENTRADA', 8, 3),
(10, '2025-08-30 19:39:42.000000', 'compra', 10, 'ENTRADA', 8, 3),
(11, '2025-09-08 22:24:16.000000', 'compra', 1, 'ENTRADA', 3, 1);

-- --------------------------------------------------------

--
-- Estrutura para tabela `produtos_tb`
--

CREATE TABLE `produtos_tb` (
  `id` int(11) NOT NULL,
  `descricao` varchar(255) DEFAULT NULL,
  `nome` varchar(255) DEFAULT NULL,
  `preco` double DEFAULT NULL,
  `quantidade` int(11) DEFAULT NULL,
  `valor_total` double DEFAULT NULL,
  `fornecedor_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Despejando dados para a tabela `produtos_tb`
--

INSERT INTO `produtos_tb` (`id`, `descricao`, `nome`, `preco`, `quantidade`, `valor_total`, `fornecedor_id`) VALUES
(1, 'Marca LG', 'TV LG', 1000, 2, 2000, 1),
(2, 'Marca chines', 'celular xiaomi', 1000, 3, 3000, 3),
(3, 'Marca Samsung', 'Celular Samsung', 1500, 11, 16500, 3),
(8, 'Marca123', 'xiaomi123', 2000, 35, 70000, 1);

-- --------------------------------------------------------

--
-- Estrutura para tabela `usuario_tb`
--

CREATE TABLE `usuario_tb` (
  `id` int(11) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `nome` varchar(255) DEFAULT NULL,
  `senha` varchar(255) DEFAULT NULL,
  `nivel_do_usuario` varchar(100) NOT NULL,
  `criado_em` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Despejando dados para a tabela `usuario_tb`
--

INSERT INTO `usuario_tb` (`id`, `email`, `nome`, `senha`, `nivel_do_usuario`, `criado_em`) VALUES
(1, 'teste@teste.com', 'admin', '$2a$10$Sphs0R/paZmEJEYd9tmSZuBTXZkxQnj50naZWKkmFUphnb4aXtTV2', 'ADMIN', '2025-08-22 12:47:45'),
(2, 'gerente@gerente.com', 'gerente', '$2a$10$8MW58wq22MBK1iCqFPq4kudk2F7UIyrurinomRmpoLNK4jO0yuvJ.', 'GERENTE', '2025-08-23 18:41:22'),
(3, 'usuario@usuario.com', 'usuario', '$2a$10$RMXMMdrKm1bXu/VWgX9QhuCll1yLpzj6muVTxgCZ7.yXNrEqlPaRa', 'USUARIO', '2025-08-23 18:41:46'),
(4, 'estoque@estoque.com', 'estoque', '$2a$10$ZkqXUnOjTmNyzJVC0d1XF.RAA4Ccn83uzg5Z4gGR0nMZCEwGZO8dC', 'ESTOQUE', '2025-08-23 18:42:09');

-- --------------------------------------------------------

--
-- Estrutura para tabela `vendas_tb`
--

CREATE TABLE `vendas_tb` (
  `id` bigint(20) NOT NULL,
  `data_venda` datetime(6) DEFAULT NULL,
  `quantidade` int(11) DEFAULT NULL,
  `valor_total_vendido` double DEFAULT NULL,
  `cliente_id` int(11) DEFAULT NULL,
  `movimentacao_id` int(11) DEFAULT NULL,
  `produto_id` int(11) DEFAULT NULL,
  `usuario_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Despejando dados para a tabela `vendas_tb`
--

INSERT INTO `vendas_tb` (`id`, `data_venda`, `quantidade`, `valor_total_vendido`, `cliente_id`, `movimentacao_id`, `produto_id`, `usuario_id`) VALUES
(1, '2025-08-27 00:17:37.000000', 2, 2100, 1, 5, 2, 1),
(2, '2025-08-29 01:38:13.000000', 5, 10500, 1, 7, 8, 1);

--
-- Índices para tabelas despejadas
--

--
-- Índices de tabela `cliente_tb`
--
ALTER TABLE `cliente_tb`
  ADD PRIMARY KEY (`id`);

--
-- Índices de tabela `config_estoque_tb`
--
ALTER TABLE `config_estoque_tb`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK9x6aicy88c0kbyjgpg4kfbpy4` (`produto_id`);

--
-- Índices de tabela `fornecedor_tb`
--
ALTER TABLE `fornecedor_tb`
  ADD PRIMARY KEY (`id`);

--
-- Índices de tabela `movimentacao_tb`
--
ALTER TABLE `movimentacao_tb`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK27jij4abtgirpq12fb0qci608` (`produto_id`),
  ADD KEY `FKdupy13hbna3afdn6dbb9m33sd` (`usuario_id`);

--
-- Índices de tabela `produtos_tb`
--
ALTER TABLE `produtos_tb`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKlmrx7yxaakaxi6typ00h4u3uc` (`fornecedor_id`);

--
-- Índices de tabela `usuario_tb`
--
ALTER TABLE `usuario_tb`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `email` (`email`);

--
-- Índices de tabela `vendas_tb`
--
ALTER TABLE `vendas_tb`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UKn0yg74lla5t7tv7k5tdsrn5kw` (`movimentacao_id`),
  ADD KEY `FK2y57gik9how6tq8vjybln3dth` (`cliente_id`),
  ADD KEY `FKhwr6t0ayec8ncc14wt8gyaxhw` (`produto_id`),
  ADD KEY `FKjb0o797tcnkqcbiuqm52prasb` (`usuario_id`);

--
-- AUTO_INCREMENT para tabelas despejadas
--

--
-- AUTO_INCREMENT de tabela `cliente_tb`
--
ALTER TABLE `cliente_tb`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT de tabela `fornecedor_tb`
--
ALTER TABLE `fornecedor_tb`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT de tabela `movimentacao_tb`
--
ALTER TABLE `movimentacao_tb`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT de tabela `produtos_tb`
--
ALTER TABLE `produtos_tb`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;

--
-- AUTO_INCREMENT de tabela `usuario_tb`
--
ALTER TABLE `usuario_tb`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT de tabela `vendas_tb`
--
ALTER TABLE `vendas_tb`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- Restrições para tabelas despejadas
--

--
-- Restrições para tabelas `movimentacao_tb`
--
ALTER TABLE `movimentacao_tb`
  ADD CONSTRAINT `FK27jij4abtgirpq12fb0qci608` FOREIGN KEY (`produto_id`) REFERENCES `produtos_tb` (`id`),
  ADD CONSTRAINT `FKdupy13hbna3afdn6dbb9m33sd` FOREIGN KEY (`usuario_id`) REFERENCES `usuario_tb` (`id`);

--
-- Restrições para tabelas `produtos_tb`
--
ALTER TABLE `produtos_tb`
  ADD CONSTRAINT `FKlmrx7yxaakaxi6typ00h4u3uc` FOREIGN KEY (`fornecedor_id`) REFERENCES `fornecedor_tb` (`id`);

--
-- Restrições para tabelas `vendas_tb`
--
ALTER TABLE `vendas_tb`
  ADD CONSTRAINT `FK2y57gik9how6tq8vjybln3dth` FOREIGN KEY (`cliente_id`) REFERENCES `cliente_tb` (`id`),
  ADD CONSTRAINT `FKggsg4s5kn2a3yjysfac1gy7vn` FOREIGN KEY (`movimentacao_id`) REFERENCES `movimentacao_tb` (`id`),
  ADD CONSTRAINT `FKhwr6t0ayec8ncc14wt8gyaxhw` FOREIGN KEY (`produto_id`) REFERENCES `produtos_tb` (`id`),
  ADD CONSTRAINT `FKjb0o797tcnkqcbiuqm52prasb` FOREIGN KEY (`usuario_id`) REFERENCES `usuario_tb` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
