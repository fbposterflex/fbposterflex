-- phpMyAdmin SQL Dump
-- version 4.0.10deb1
-- http://www.phpmyadmin.net
--
-- Servidor: localhost
-- Tiempo de generación: 30-03-2016 a las 17:53:33
-- Versión del servidor: 5.5.47-0ubuntu0.14.04.1
-- Versión de PHP: 5.5.9-1ubuntu4.14

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Base de datos: `fbposterflex`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `token`
--

CREATE TABLE IF NOT EXISTS `token` (
  `idToken` int(10) NOT NULL AUTO_INCREMENT,
  `token` varchar(100) NOT NULL,
  `fechaCreacion` date NOT NULL,
  `fechaExpira` date NOT NULL,
  `activo` bit(1) NOT NULL,
  `idUsuario` int(10) NOT NULL,
  PRIMARY KEY (`idToken`),
  KEY `idUsuario` (`idUsuario`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=5 ;

--
-- Volcado de datos para la tabla `token`
--

INSERT INTO `token` (`idToken`, `token`, `fechaCreacion`, `fechaExpira`, `activo`, `idUsuario`) VALUES
(1, 'o+4OL+4L3UL+3sRafDAdRd/Nr+UabM2p/Zh3PS+p6YCpJ7rGyHm+1YI9tXdX5fPonXoSMSsN+3yeNannjK78Mg==', '2016-03-30', '2016-03-30', b'0', 1),
(2, '1lK+AgAYjWu7Sea64JlJhDBeXBYcQ/vhUlTDN1uldlxBrw4GpYq9m5ZeYf6xcYlXll6K1diO+g+JRf6wLWS/kg==', '2016-03-30', '2016-03-30', b'0', 1),
(3, 'JdvxXkPMgQW6G0jvp3Jf9NfNQRs9qV8CaQ+wfhuZB+fMHcbd6vm/JsjPAK86ckmU457iZRA6nQ+g9tsduk2oSA==', '2016-03-30', '2016-03-30', b'0', 1),
(4, 'cKnn7aj0i2jeYK6rMHzifHU0KKOo960GmLYW0BK5DRKFcnsexVZnqF3vdEIxpwRCj8k+V1xL2Xjq5SAuOOy7wQ==', '2016-03-30', '2016-03-30', b'0', 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuarios`
--

CREATE TABLE IF NOT EXISTS `usuarios` (
  `idUsuario` int(10) NOT NULL AUTO_INCREMENT,
  `userName` varchar(30) NOT NULL,
  `password` varchar(100) NOT NULL,
  `activo` bit(1) NOT NULL,
  `nombre` varchar(40) NOT NULL,
  `correo` varchar(30) NOT NULL,
  `fechaAlta` date NOT NULL,
  PRIMARY KEY (`idUsuario`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

--
-- Volcado de datos para la tabla `usuarios`
--

INSERT INTO `usuarios` (`idUsuario`, `userName`, `password`, `activo`, `nombre`, `correo`, `fechaAlta`) VALUES
(1, 'admin', 'wuYszdFVbfA9ykHukCSiJiVqmglur7AOV7Jt0yfx4uwIA2p1EErVF4AbOOUX+UbYKFOmSRjl4bJf//+WTkT9zg==', b'1', 'Administrador', 'perezcruzluisalfonso@gmail.com', '2016-03-30');

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `token`
--
ALTER TABLE `token`
  ADD CONSTRAINT `token_ibfk_1` FOREIGN KEY (`idUsuario`) REFERENCES `usuarios` (`idUsuario`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
