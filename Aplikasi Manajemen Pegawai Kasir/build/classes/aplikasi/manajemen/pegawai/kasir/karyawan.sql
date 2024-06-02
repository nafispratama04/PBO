-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Waktu pembuatan: 27 Bulan Mei 2024 pada 06.46
-- Versi server: 10.4.27-MariaDB
-- Versi PHP: 8.2.0

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `karyawan`
--

-- --------------------------------------------------------

--
-- Struktur dari tabel `akun`
--

CREATE TABLE `akun` (
  `No_telp` VARCHAR(100) NOT NULL,
  `Username` VARCHAR(100) DEFAULT NULL,
  `Password_` VARCHAR(100) DEFAULT NULL,
  `Nama` VARCHAR(100) DEFAULT NULL,
  `email` VARCHAR(100) NOT NULL,
  `NIK` VARCHAR(100) DEFAULT NULL
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Indexes for dumped tables
--

--
-- Indeks untuk tabel `akun`
--
ALTER TABLE `akun`
  ADD PRIMARY KEY (`No_telp`),
  ADD UNIQUE KEY `email` (`email`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

CREATE TABLE PegawaiKasir (
    ID INT PRIMARY KEY AUTO_INCREMENT,
    Nama VARCHAR(100) NOT NULL,
    NomorPegawai VARCHAR(20) NOT NULL UNIQUE,
    TempatLahir VARCHAR(100) NOT NULL,
    TanggalLahir DATE NOT NULL,
    Alamat TEXT NOT NULL,
    NomorTelepon VARCHAR(20) NOT NULL,
    Email VARCHAR(100),
    Gaji DECIMAL(10, 2) NOT NULL,
    TanggalMulai DATE NOT NULL
) ENGINE=INNODB;

CREATE TABLE JadwalKerja (
    ID INT PRIMARY KEY AUTO_INCREMENT,
    NomorPegawai VARCHAR(20) NOT NULL,
    HariKerja VARCHAR(50),
    FOREIGN KEY (NomorPegawai) REFERENCES PegawaiKasir(NomorPegawai)
) ENGINE=INNODB;

CREATE TABLE JadwalPelatihan (
    ID INT PRIMARY KEY AUTO_INCREMENT,
    NomorPegawai VARCHAR(20) NOT NULL,
    MingguPelatihan VARCHAR(50),
    TopikPelatihan VARCHAR(100),
    FOREIGN KEY (NomorPegawai) REFERENCES PegawaiKasir(NomorPegawai)
) ENGINE=INNODB;

CREATE TABLE AbsensiPegawai (
    ID INT PRIMARY KEY AUTO_INCREMENT,
    NomorPegawai VARCHAR(20) NOT NULL,
    Tanggal DATE NOT NULL,
    Hari VARCHAR(10) NOT NULL,
    KeteranganAbsensi VARCHAR(20) NOT NULL,
    FOREIGN KEY (NomorPegawai) REFERENCES PegawaiKasir(NomorPegawai)
) ENGINE=INNODB;

CREATE TABLE GajiPembayaran (
    ID INT PRIMARY KEY AUTO_INCREMENT,
    NomorPegawai VARCHAR(20) NOT NULL,
    BulanTahun VARCHAR(20) NOT NULL,
    GajiDasar DECIMAL(10, 2) NOT NULL,
    PersentaseKehadiran DECIMAL(5, 2) NOT NULL,
    GajiDibayar DECIMAL(10, 2) NOT NULL,
    TanggalPembayaran DATE NOT NULL,
    FOREIGN KEY (NomorPegawai) REFERENCES PegawaiKasir(NomorPegawai)
) ENGINE=INNODB;