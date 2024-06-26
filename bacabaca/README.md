---
## Tutorial 5
### What I have learned today
Web Service, Postman, Rest API

### Pertanyaan
1. Apa itu Postman? Apa kegunaannya?

Postman adalah aplikasi komputer yang digunakan untuk pengujian API. Postman digunakan untuk mengirim permintaan API ke server web dan menerima respons, apa pun itu.

2. Apa yang terjadi ketika kita tidak menggunakan @JsonIgnoreProperties dan @JsonProperty pada model Buku dan Penulis? apabila terjadi error, mengapa hal tersebut dapat terjadi?

`@JsonIgnoreProperties`

Fungsi: mengabaikan properti tertentu pada objek Java saat melakukan marshalling (konversi objek Java menjadi JSON) atau unmarshalling (konversi JSON menjadi objek Java).

Jika tidak digunakan, properti yang tidak cocok akan dimasukkan ke dalam JSON, sehingga bisa menyebabkan masalah format.

`@JsonProperty`

Fungsi: mengatur nama yang digunakan untuk properti pada objek Java dalam JSON yang dihasilkan. Ini memberikan fleksibilitas dalam mengontrol nama properti dalam JSON tanpa mengubah nama properti di kelas Java.

Jika tidak digunakan, default nama properti di kelas Java akan digunakan dalam JSON.

3. Pada tutorial ini, kita mencoba untuk memanggil data dengan menggunakan method GET. Namun, apakah kita dapat memanggil data dengan method lainya, seperti POST? Jelaskan pendapat kalian?

Method GET digunakan untuk membaca data dan tidak mengubah data yang ada di server. Untuk mengirim (create) data baru atau mengubah (update) data yang sudah ada di server, kita dapat menggunakan method POST (atau PUT dan DELETE sesuai kebutuhan). Method POST digunakan untuk mengirimkan data baru ke server, method PUT digunakan untuk memperbarui data yang sudah ada, dan method DELETE digunakan untuk menghapus data. 

Jadi, apakah kita dapat memanggil data dengan method selain GET?

Ya, kita dapat menggunakan method POST (dan method lainnya) untuk mengirimkan data ke server atau mengubah data yang ada. Namun, perlu pengimplementasian endpoint yang sesuai dengan method terkait di server kita agar permintaan tersebut dapat diproses.

4. Selain method GET dan POST, sebutkan dan jelaskan secara singkat HTTP request methods lainnya yang dapat kita gunakan!

`PUT` : Method PUT digunakan untuk memperbarui data yang ada di server. Ketika kita mengirim permintaan PUT, kita menggantikan data di server dengan data yang dikirim. Method ini sering digunakan untuk operasi update.

`DELETE` : Method DELETE digunakan untuk menghapus sumber daya dari server. Ketika kita mengirim permintaan DELETE, kita meminta server untuk menghapus sumber daya yang ditentukan. Method ini sering digunakan untuk menghapus data atau entitas tertentu.

`PATCH` : Method PATCH digunakan untuk memperbarui data sebagian dari sumber daya yang ada di server. Ini berguna ketika kita hanya ingin memperbarui beberapa bagian kecil dari sumber daya tanpa menggantikan seluruhnya.

`HEAD` : Method HEAD hampir mirip dengan GET, tetapi server hanya akan mengirimkan header respons tanpa tubuh (body) pesan. Ini berguna untuk memeriksa informasi header (seperti status kode atau header konten) tanpa mengunduh seluruh konten.

`OPTIONS` : Method OPTIONS digunakan untuk mendapatkan informasi tentang metode-metode yang didukung oleh sumber daya atau endpoint tertentu. Ini berguna untuk mengetahui apa yang bisa dilakukan dengan sumber daya tersebut.

`CONNECT` : Method CONNECT digunakan untuk menginisiasi koneksi ke sumber daya jarak jauh, seperti server proxy atau gateway. Ini digunakan dalam konteks tuneling, misalnya untuk mengakses sumber daya di balik firewall.

`TRACE` : Method TRACE digunakan untuk mengembalikan permintaan yang diterima oleh server kembali ke pengirimnya. Ini digunakan untuk tujuan debugging dan diagnosa.

5. Apa kegunaan atribut WebClient?

`WebClient` adalah komponen atau objek yang memungkinkan kita untuk membuat permintaan HTTP ke server dan mengelola respons yang diterima dari server.

Kegunaan atribut WebClient adalah sebagai berikut.
- Membuat HTTP Request dengan memanfaatkan method GET, POST, PUT, DELETE, dan lainnya ke server.
- Mengirim data dalam berbagai format, seperti JSON, XML, dan lainnya.
- WebClient dapat membantu kita dalam mengelola respons yang diterima dari server.
- WebClient juga mendukung operasi asynchronous sehingga aplikasi dapat tetap responsif sambil menunggu respons dari server.
- WebClient dapat digunakan untuk berinteraksi dengan berbagai jenis layanan web, salah satunya adalah Restful API.

### What I did not understand
- [ ] semuanya

---
## Tutorial 4
### What I have learned today
Presentation Layer & Spring Profiles

### Pertanyaan
1. Pada file html project bacabaca, terdapat baris kode berikut.
`<html lang="en" xmlns="http://www.w3.org/1999/xhtml" xmlns:th="http://thymeleaf.org">`
Apa itu xmlns? Jawab dengan singkat dan padat.

xmlns adalah singkatan dari XML Namespace. Atribut ini digunakan dalam dokumen XML atau XHTML untuk mendefinisikan namespace yang digunakan dalam dokumen tersebut.

- `html lang="en"` : mengatur bahasa utama yang digunakan dalam dokumen HTML ke bahasa Inggris (English)
- `xmlns="http://www.w3.org/1999/xhtml"` : mendefinisikan namespace utama dokumen sebagai XHTML, yaitu versi XML dari HTML
- `xmlns:th="http://thymeleaf.org"` : enambahkan namespace dengan alias "th" yang mengacu pada namespace untuk Thymeleaf

2. Jelaskan perbedaan th:include dan th:replace! Jawab dengan singkat dan padat.

- `th:include` : Menggantikan konten elemen target dengan konten dari sumber yang disebutkan. Elemen target tetap ada dalam dokumen dengan kontennya yang digantikan.
- `th:replace` : Sepenuhnya menggantikan elemen target dengan konten dari sumber yang disebutkan. Elemen target tidak akan ada dalam dokumen hasil.

3. Kapan sebaiknya kita menggunakan static files dibandingkan dengan file eksternal menggunakan link?  Jawab dengan singkat dan padat.

Kita sebaiknya menggunakan `static files` ketika konten tersebut stabil, tidak berubah-ubah, dan tidak memerlukan interaksi server. Menggunakan link untuk `file eksternal` lebih cocok ketika konten tersebut dinamis atau perlu diambil dari sumber eksternal, seperti data yang diperbarui secara berkala atau perlu disesuaikan dengan pengguna tertentu.

4. Jelaskan caramu menyelesaikan latihan no 2.

Menambahkan kode berikut pada `BukuController.java`
```BukuController.java
@PostMapping(value = "buku/update", params = {"addRow"})
public String addRowPenulisBuku(@ModelAttribute UpdateBukuRequestDTO updateBukuRequestDTO, Model model){
   if(updateBukuRequestDTO.getListPenulis() == null || updateBukuRequestDTO.getListPenulis().size() == 0){
      updateBukuRequestDTO.setListPenulis(new ArrayList<>());
   }

   updateBukuRequestDTO.getListPenulis().add(new Penulis());

   model.addAttribute("listPenulisExisting", penulisService.getAllPenulis());
   model.addAttribute("listPenerbit", penerbitService.getAllPenerbit());
   model.addAttribute("bukuDTO", updateBukuRequestDTO);

   model.addAttribute("activePage", "Buku");
   return "form-update-buku";
}

@PostMapping(value = "buku/update", params = {"deleteRow"})
public String deleteRowPenulisBuku(@ModelAttribute UpdateBukuRequestDTO updateBukuRequestDTO, @RequestParam("deleteRow") int row, Model model){
   updateBukuRequestDTO.getListPenulis().remove(row);

   model.addAttribute("bukuDTO", updateBukuRequestDTO);
   model.addAttribute("listPenulisExisting", penulisService.getAllPenulis());
   model.addAttribute("listPenerbit", penerbitService.getAllPenerbit());

   model.addAttribute("activePage", "Buku");
   return "form-update-buku";
}
```

Menambahkan kode berikut pada `form-update-buku.html`
```form-update-buku.html
Penulis: <br>
<table class="table">
<th class="d-flex justify-content-end">
   <button class="btn btn-primary" type="submit" name="addRow" style="text-align: right;">Tambah Row</button>
</th>

<tbody>
<tr th:each="penulis, iterationStatus : *{listPenulis}">
   <td>
      <select th:field="*{listPenulis[__${iterationStatus.index}__].idPenulis}" class="form-control">
      <div th:each="penulisExisting : ${listPenulisExisting}">
         <option th:value="${penulisExisting.idPenulis}" th:text="${penulisExisting.namaPenulis}"></option>
      </div>
      </select>
   </td>

   <td>
      <button th:value="${iterationStatus.index}" class="btn btn-danger" type="submit" name="deleteRow">
      Hapus
      </button>
   </td>
</tr>
</tbody>
</table>
```

5. Jelaskan apa itu pagination! Jawab dengan singkat dan padat.

Pagination adalah teknik untuk membagi konten atau data menjadi beberapa halaman yang dapat diakses secara terpisah sehingga memungkinkan pengguna untuk menavigasi dan melihat data dalam jumlah besar dengan cara yang lebih teratur, membaginya menjadi halaman-halaman yang lebih kecil dan dapat diakses dengan tombol atau tautan.

6. Sebutkan salah satu skenario yang mengharuskan adanya perbedaan dev dan prod dan jelaskan alasannya!

Skenario : penggunaan database
Pada `prod`, database dapat berisi data pengguna yang sensitif, seperti informasi pribadi atau keuangan. Pada `dev`, data ini harus disaring untuk melindungi privasi pengguna dan mematuhi regulasi privasi data.

Oleh karena itu, database `dev` mungkin menggunakan dummy data atau data yang telah diubah sedemikian rupa sehingga tidak sensitif, sedangkan database `prod` akan memiliki akses sesungguhnya ke data-data yang sensitif. Hal ini juga melibatkan konfigurasi koneksi database yang berbeda antara dev dan prod untuk mengarahkan ke database yang sesuai.

7. Lampirkan screenshot kalau kamu sudah berhasil membuat user untuk environment production serta bukti bahwa kamu sudah berhasil mengakses database production dengan user tersebut!

![Alt text](<skrinsut nomor 7.png>)

### What I did not understand
- [ ] Masih bingung cara switch dari dev ke prod gimana

---
## Tutorial 3
### What I have learned today
Database, Mapper, dan Repository

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

---
## Tutorial 2
### What I have learned today
Model, Service, Controller, dan Gradle

### Pertanyaan
1. Apa itu DTO? Jelaskan kegunaannya pada proyek ini?

DTO adalah singkatan dari Data Transfer Object yang digunakan untuk mengirim data antara service dan controller. Pada proyek ini, DTO digunakan untuk menyimpan informasi mengenai sebuah buku.

2. Apa itu UUID? Mengapa UUID digunakan?

UUID adalah singkatan dari Universally Unique Identifier yang digunakan untuk mengidentifikasi objek atau entitas secara unik di berbagai sistem komputer. Contoh UUID adalah "550e8400-e29b-41d4-a716-446655440000". Singkatnya, UUID digunakan karena bisa menghasilkan identifikasi yang unik dan tahan lama untuk berbagai jenis entitas atau objek dalam sistem komputer yang berbeda-beda.

3. Pada service, mengapa perlu ada pemisahan antara interface dan implementasinya?

Pemisahan antara interface dan implementasinya diperlukan agar kita dapat mengembangkan kode mengacu kepada interface tanpa perlu menunggu implementasi selesai. Hal ini juga memudahkan penyediaan variasi service yang berbeda, di mana kita dapat memiliki beberapa implementasi yang berbeda dari interface yang sama untuk kebutuhan yang berbeda. Pemisahan ini mendukung pengembangan paralel dalam tim.

4. Menurut kamu anotasi @Autowired pada class Controller tersebut merupakan implementasi dari konsep apa? Dan jelaskan secara singkat cara kerja @Autowired tersebut dalam konteks service dan controller yang telah kamu buat.

Anotasi @Autowired yang digunakan pada class Controller tersebut merupakan implementasi dari konsep Dependency Injection.

Cara kerja @Autowired:
- Pada class `BukuService`, anotasi @Autowired digunakan pada atribut private BukuService bukuService. Saat aplikasi dijalankan, Spring Container akan mencari bean yang sesuai dengan tipe BukuService yang dideklarasikan. Setelah menemukan bean tersebut (di sini BukuServiceImpl), Spring Container secara otomayttis akan meng-inject instance bean ke dalam atribut bukuService.
- Pada class `BukuController`, anotasi @Autowired juga digunakan untuk injeksi class BukuService. Ketika permintaan HTTP yang membutuhkan layanan dari BukuService diterima, Spring Controller akan menggunakan instance bukuService yang sudah diinjeksi. Spring Controller dapat memanggil metode-metode dari bukuService tanpa harus membuat objek BukuService secara manual.

5. Apa perbedaan @GetMapping dan @PostMapping?

`@GetMapping` digunakan untuk permintaan GET yang membaca data, sementara `@PostMapping` digunakan untuk permintaan POST yang melakukan perubahan atau mengirim data ke server.

6. Jelaskan proses yang terjadi di controller, model, dan service pada proses create buku, mulai dari fungsi formAddBuku hingga pengguna menerima halaman success-create-buku.

- BukuController : 

Ketika pengguna mengakses URL `/buku/create` dengan metode HTTP GET, Controller menerima permintaan ini melalui metode formAddBuku. Di dalam metode formAddBuku, Controller membuat objek BukuDTO yang digunakan sebagai data isian form yang akan ditampilkan kepada pengguna. Ini dilakukan dengan membuat instance BukuDTO dan menambahkannya ke model dengan nama "bukuDTO" menggunakan model.addAttribute(). Setelah menyiapkan data form, Controller mengembalikan string "form-create-buku".

- form-create-buku : 

Template form-create-buku akan merender halaman HTML form yang berisi input untuk data buku seperti judul, penulis, tahun terbit, dan harga. Data ini diambil dari objek BukuDTO yang telah dimasukkan ke model. Pengguna dapat mengisi form yang tersedia pada halaman ini.

- BukuController : 

Ketika pengguna mengisi form dan mengirimkannya dengan metode POST, Controller menerima permintaan ini melalui metode addBuku. Di dalam metode addBuku, Controller pertama-tama menghasilkan UUID baru untuk buku yang akan dibuat dengan menggunakan UUID.randomUUID() yang akan menjadi ID unik untuk buku yang akan disimpan. Kemudian, Controller memeriksa apakah judul buku yang akan dibuat sudah ada dalam daftar buku yang ada menggunakan metode isJudulExist() dari BukuService. Jika judul sudah ada, maka Controller menambahkan pesan kesalahan ke model dan mengembalikan string "error-view". Ini akan mengarahkan pengguna ke halaman error. Jika judul belum ada, Controller membuat objek Buku dengan data yang diambil dari BukuDTO yang dikirim dari form. Kemudian, Controller memanggil metode createBuku() dari BukuService untuk menyimpan buku baru tersebut. Setelah buku berhasil dibuat, Controller menambahkan ID dan judul buku ke model untuk ditampilkan pada halaman success-create-buku. Controller juga akan mengembalikan string "success-create-buku".

- success-create-buku : 

Template success-create-buku akan merender halaman yang mengonfirmasi kepada pengguna bahwa buku telah berhasil dibuat. Informasi ID dan judul buku yang telah dibuat akan ditampilkan kepada pengguna sehingga pengguna akan melihat halaman ini sebagai respons bahwa buku berhasil dibuat.

7. Jelaskan mengenai th:object!

`th:object` adalah atribut yang digunakan untuk mengaitkan objek Java dengan elemen HTML tertentu dalam template Thymeleaf. Atribut ini dapat digunakan dalam elemen seperti "form", "div", atau elemen lain yang membutuhkan data objek. Atribut ini memungkinkan kita untuk menghubungkan data objek dengan elemen-elemen HTML sehingga dapat melakukan rendering data dinamis atau pembaruan data saat pengguna berinteraksi dengan halaman web.

8. Jelaskan mengenai th:field!
TIPS: Buka “view page source” dari halaman Tambah Buku di browser. Lakukan screenshot kemudian jelaskan temuan kalian.

`th:field` adalah atribut yang digunakan untuk mempermudah binding data objek ke elemen HTML form, khususnya dalam form handling. Atribut ini akan menghasilkan atribut `name` dan `id` dalam elemen HTML input atau elemen form lainnya secara otomatis berdasarkan nama properti objek yang terkait dengan form tersebut. Dengan kata lain, atribut ini membuat binding antara properti objek dan elemen HTML sehingga saat data form dikirimkan, Thymeleaf akan dapat mengenali dan menghubungkan data tersebut dengan objek Java yang sesuai.

![Alt text](<Screenshot (3723).png>)

Berdasarkan screenshot tersebut, terdapat perubahan pada kode html yang semula th:field="*{judul}". 
- Line 20 berubah menjadi id="judul" name="judul" value=""
- Line 24 berubah menjadi id="penulis" name="penulis" value=""
- Line 28 berubah menjadi id="tahunTerbit" name="tahunTerbit" value=""
- Line 32 berubah menjadi id="harga" name="harga" value="0"

### What I did not understand
- [ ] Kalo @GetMapping atau @PostMapping dll nya ga ditulis emang ngaruh?

---
## Tutorial 1
### What I have learned today
Saya belajar mengenai dasar-dasar git dan spring boot ^_^

### GitLab
1. Apa itu Issue? Apa saja masalah yang dapat diselesaikan dengan fitur Issue?

"Issue" merujuk pada suatu masalah, perbaikan, atau tugas yang terdokumentasi dalam sistem pelacakan masalah atau manajemen proyek. Fitur "Issue" digunakan untuk melacak dan mengelola berbagai jenis masalah yang terkait dengan pengembangan proyek. Masalah yang dapat diselesaikan dengan fitur ini adalah sebagai berikut.

- Bug Tracking: memprioritaskan perbaikan yang harus dilakukan dan memastikan bahwa masalah-masalah tersebut harus diselesaikan.
- Diskusi dan Komentar: anggota tim dapat berkomunikasi, memberi masukan, komentar, atau menyelesaikan suatu masalah bersama.
- Pelacakan Progress: setiap Issue biasanya memiliki status yang memungkinkan tim untuk melihat di mana setiap masalah berada dalam proses pengembangan.

3. Apa perbedaan dari git merge dan git merge --squash?

`git merge` digunakan untuk menggabungkan seluruh riwayat komit dari cabang sumber ke dalam cabang saat ini, sedangkan `git merge --squash` digunakan untuk menggabungkan perubahan dari cabang sumber ke dalam cabang saat ini sebagai satu komit tunggal.

5. Apa keunggulan menggunakan Version Control System seperti Git dalam pengembangan suatu aplikasi?

- Git memungkinkan kita untuk melacak setiap perubahan yang telah dilakukan pada aplikasi. Riwayat tersebut dapat kita gunakan untuk debugging, pengembangan proyek, dan sebagainya.
- Git juga memudahkan kita untuk berkolaborasi dengan orang-orang yang terlibat dalam satu proyek yang sama dengan memanfaatkan fitur branch dan merge.
- Melalui Git, kita juga bisa melakukan manajemen konflik dengan mudah. Misalnya, kita dapat mengakses versi sebelumnya dari kode saat ini.


### Spring
1. Apa itu library & dependency?

Library adalah kode yang digunakan oleh pengembang untuk memperluas fungsionalitas aplikasi mereka, sementara dependency adalah komponen eksternal yang diperlukan untuk menjalankan perangkat lunak tersebut.

2. Apa itu Maven? Mengapa kita menggunakan Maven? Apakah ada alternatif dari Maven?

Maven adalah salah satu alat manajemen proyek yang digunakan dalam pengembangan perangkat lunak berbasis Java. Ini adalah alat open-source yang dikembangkan oleh Apache Software Foundation.

Alasan penggunaan Maven:
- Manajemen dependensi: Maven memungkinkan pengembang untuk dengan mudah mengelola dan menyatakan dependensi proyek, yang adalah komponen atau pustaka eksternal yang dibutuhkan oleh proyek.
- Proyek yang terstruktur: Hal ini memudahkan pengembang untuk mengatur kode dengan cara yang konsisten dan mudah dipahami karena mencakup kode sumber, sumber daya, tes, dan dokumen proyek.
- Manajemen proyek: Maven memungkinkan pengembang untuk mengelola proyek secara terpusat melalui file konfigurasi pom.xml (Project Object Model) yang mendefinisikan konfigurasi proyek, dependensi, dan tugas-tugas yang akan dijalankan.

Alternatif Maven --> Gradle

Gradle adalah alat manajemen proyek lain yang semakin populer dalam komunitas pengembangan Java. Ini menggunakan DSL (Domain Specific Language) berbasis Groovy atau Kotlin untuk mendefinisikan struktur proyek dan tugas-tugas. Gradle memiliki fleksibilitas yang tinggi dan dapat digunakan untuk berbagai jenis proyek, bukan hanya Java.

3. Jelaskan bagaimana alur ketika kita menjalankan http://localhost:8080/celsius-converter/90 sampai dengan muncul keluarannya di browser!

- HTTP Request, browser membuat permintaan HTTP GET ke server lokal pada port 8080.
- Request Handling, Spring Boot akan menggunakan mekanisme routing untuk mencocokkan URL dengan metode yang sesuai untuk menangani permintaan tersebut. URL /celsius-converter/90 mungkin cocok dengan suatu kontroler atau endpoint yang sudah didefinisikan dalam aplikasi.
- Controller Processing, Setelah URL cocok dengan kontroler yang sesuai, function konversi suhu dalam kontroler akan dipanggil untuk menangani permintaan.
- Data Processing and Business Logic, ketika aplikasi menerima nilai 90 maka aplikasi akan melakukan konversi terhadap angka tersebut sesuai dengan permintaan (kelvin, fahrenheit, atau yang lainnya)
- Response Generation, setelah konversi suhu selesai, hasilnya akan digunakan untuk membuat objek respon HTTP.
- Sending Response to Browser, setelah objek respon dibuat, server akan mengirimkannya kembali ke browser sebagai respons HTTP.
- Rendering in Browser, browser merender halaman HTML
- Display in Browser, hasil konversi suhu akan ditampilkan di browser.

4. Selain untuk pengembangan web, apa saja yang bisa dikembangkan dengan Spring framework?

- Aplikasi Berbasis Mikroservis : 

Spring Boot, yang merupakan bagian dari ekosistem Spring, adalah pilihan yang sangat populer untuk mengembangkan mikroservis. Dengan Spring Boot, Anda dapat dengan cepat membuat dan mengelola berbagai mikroservis yang menjalankan fungsi-fungsi terpisah dalam aplikasi yang lebih besar. 

- Aplikasi Berbasis REST API : 
Spring Framework menyediakan dukungan yang kuat untuk mengembangkan RESTful API. Anda dapat menggunakan Spring MVC atau Spring WebFlux (untuk pemrosesan non-blocking) untuk membuat API yang efisien dan dapat diandalkan. 

- Aplikasi Berbasis Batch : 

Spring Batch adalah subproyek Spring yang menyediakan kerangka kerja untuk mengembangkan aplikasi pemrosesan batch. Ini cocok untuk tugas-tugas yang memerlukan pengolahan data besar dalam batch, seperti pengimporan data, pengolahan transaksi, atau pelaporan.

5. Apa perbedaan dari @RequestParam dan @PathVariable? Kapan sebaiknya menggunakan @RequestParam atau @PathVariable?

@RequestParam digunakan untuk mengakses nilai melalui query parameter, sedangkan @PathVariable digunakan untuk mengakses nilai dari template URI.

### What I did not understand
(tuliskan apa saja yang kurang Anda mengerti, Anda dapat mencentang apabila Anda sudah mengerti dikemudian hari, dan tambahkan tulisan yang membuat Anda mengerti)
- [ ] Kenapa namanya APAP?