---
## Tutorial 3
### What I have learned today
Database, Mapper, Repository

### Pertanyaan
1. Jelaskan apa itu ORM pada spring serta apa fungsi dan kegunaanya?

ORM (Object Relational Mapping) merupakan teknik mengubah suatu table menjadi sebuah object yang nantinya mudah untuk digunakan. Object yang dibuat akan memiliki property yang sama dengan field-field yang ada pada table tersebut. ORM memungkinkan kita melakukan query dan memanipulasi data di database menggunakan object oriented.

Kelebihan:
- Terdapat banyak fitur seperti transactions, connection pooling, migrations, seeds, streams, dan lain sebagainya
- Meminimalisir kesalahan dalam penulisan query secara manual
- Memungkinkan kita memanfaatkan OOP (object oriented programming) dengan baik
- Membuat akses data menjadi lebih abstrak (kita dapat mengubahnya kapanpun) dan portable

Kekurangan:
- Konfigurasi awal ORM sulit
- Perlu adanya training dan riset di team sebelum menggunakan ORM sehingga akan memakan waktu dalam pengerjaan suatu project
- ORM mengurangi performance dari suatu proses
- Penggunaan ORM dapat membuat kita kurang memahami struktur database dan query SQL

2. Jelaskan secara singkat apa itu dan kegunaan dari tag-tag dibawah ini.

Tag-tag berikut merupakan contoh implementasi dari ORM yang memungkinkan kita untuk melakukan pemetaan objek ke relasional dengan lebih fleksibel dan dapat dikustomisasi sesuai dengan kebutuhan.

- @Entity : Representasi dari tabel dalam suatu basis data. Digunakan untuk menandai sebuah kelas sebagai entitas dalam data model yang akan di-mapping ke sebuah tabel di dalam basis data.
- @Table : Digunakan untuk mengkustomisasi pemetaan antara sebuah kelas entitas dengan tabel dalam basis data. Selain itu, anotasi ini juga bisa digunakan untuk menentukan nama tabel yang digunakan, skema database, index, dan lain-lain.
- @Column : Digunakan untuk mengkustomisasi pemetaan antara atribut dalam kelas entitas dengan kolom dalam tabel database. Anotasi ini dapat juga digunakan untuk nama kolom, tipe data, panjang maksimum, dan lainnya.

3. Pada relasi buku ke penulis, terdapat tag
   @JoinTable(name = "penulis_buku", joinColumns = @JoinColumn(name = "id"),
   inverseJoinColumns = @JoinColumn(name = "id_penulis"))
   Jelaskan maksud dari tag @JoinTable tersebut beserta parameternya (name, joinColumns, inverseJoinColumns) dan implementasinya pada database.

Anotasi @JoinTable digunakan untuk mendefinisikan tabel penghubung yang digunakan dalam relasi many-to-many antara dua entitas yang berbeda.
- `name = "penulis_buku"` : Parameter untuk menentukan nama tabel yang akan digunakan. Pada soal ini, nama tabel yang akan digunakan adalah penulis_buku.
- `joinColumns = @JoinColumn(name = "id")` : Parameter untuk menentukan kolom dalam tabel yang akan menghubungkan entitas yang terdapat anotasi `@JoinTable` dengan entitas di sisi pertama. Pada soal ini, kolom id akan menjadi foreign key dari entitas buku.
- `inverseJoinColumns = @JoinColumn(name = "id_penulis")` : Parameter untuk menentukan kolom dalam tabel yang akan menghubungkan entitas yang terdapat anotasi `@JoinTable` dengan entitas di sisi kedua. Pada soal ini, kolom id_penulis akan menjadi foreign key dari entitas penulis.

4. Bagaimana cara kerja dari dependensi java mapper, yaitu mapstruct?

MapStruct adalah framework yang dapat digunakan untuk mempermudah pemetaan objek-ke-objek. Pada tutorial ini, MapStruct digunakan ketika mengubah suatu entitas ke DTO atau saat melakukan mapping dari satu model objek ke model objek lainnya.

Cara kerja :
- Anotasi `@Mapper` digunakan untuk mendefinisikan interface yang akan digunakan untuk mendeklarasikan method-method untuk mapping. Pada tutorial ini, anotasi `@Mapper(componentModel = "spring")` digunakan untuk melakukan pemetaan objek antara beberapa jenis DTO dan entitas Buku.
- Salah satu method yang terdapat dalam interface `BukuMapper` adalah `Buku updateBukuRequestDTOToBuku(UpdateBukuRequestDTO updateBukuRequestDTO);`. Method ini akan melakukan pemetaan dari objek `UpdateBukuRequestDTO` ke entitas `Buku`. Method ini dapat digunakan untuk memperbarui objek `Buku` yang ada berdasarkan data yang diterima.

5. Apa keuntungan dari implementasi soft delete?

Soft delete merupakan teknik menghapus data tetapi tidak benar-benar dihapus tetapi tidak ditampilkan kepada pengguna, biasanya menggunakan kolom status yang menunjukan aktif atau tidak aktif.

Kelebihan :
- Jika suatu saat data yang dihapus dibutuhkan kembali, data tersebut bisa dikembalikan
- Pelacakan data sangat detail karena tidak ada riwayat yang hilang

Kekurangan :
- Data yang terkumpul lebih besar
- Proses muat data lebih berat
- Pemrograman lebih rumit karena setiap menampilkan data harus menyisipkan filter data yang aktif saja

### What I did not understand
- [ ] Gimana sih cara bedain kapan pake @NotNull atau @NotBlank buat validasi input?
- [ ] Kenapa pas pake @Getter @Setter itu kadang method getter setter nya ada yang kedetect ada yang enggak?
- [ ] Kok bisa sih nama method di repository ngaruh sama outputnya?