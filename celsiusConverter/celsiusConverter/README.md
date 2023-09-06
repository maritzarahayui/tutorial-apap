---
## Tutorial 1
### What I have learned today
Saya belajar mengenai dasar-dasar git dan spring boot ^_^

### GitLab
1. Apa itu Issue? Apa saja masalah yang dapat diselesaikan dengan fitur Issue? \
"Issue" merujuk pada suatu masalah, perbaikan, atau tugas yang terdokumentasi dalam sistem pelacakan masalah atau manajemen proyek. Fitur "Issue" digunakan untuk melacak dan mengelola berbagai jenis masalah yang terkait dengan pengembangan proyek. Masalah yang dapat diselesaikan dengan fitur ini adalah sebagai berikut.
- Bug Tracking: memprioritaskan perbaikan yang harus dilakukan dan memastikan bahwa masalah-masalah tersebut harus diselesaikan.
- Diskusi dan Komentar: anggota tim dapat berkomunikasi, memberi masukan, komentar, atau menyelesaikan suatu masalah bersama.
- Pelacakan Progress: setiap Issue biasanya memiliki status yang memungkinkan tim untuk melihat di mana setiap masalah berada dalam proses pengembangan.

3. Apa perbedaan dari git merge dan git merge --squash? \
`git merge` digunakan untuk menggabungkan seluruh riwayat komit dari cabang sumber ke dalam cabang saat ini, sedangkan `git merge --squash` digunakan untuk menggabungkan perubahan dari cabang sumber ke dalam cabang saat ini sebagai satu komit tunggal.

5. Apa keunggulan menggunakan Version Control System seperti Git dalam pengembangan suatu aplikasi?
- Git memungkinkan kita untuk melacak setiap perubahan yang telah dilakukan pada aplikasi. Riwayat tersebut dapat kita gunakan untuk debugging, pengembangan proyek, dan sebagainya.
- Git juga memudahkan kita untuk berkolaborasi dengan orang-orang yang terlibat dalam satu proyek yang sama dengan memanfaatkan fitur branch dan merge.
- Melalui Git, kita juga bisa melakukan manajemen konflik dengan mudah. Misalnya, kita dapat mengakses versi sebelumnya dari kode saat ini.


### Spring
1. Apa itu library & dependency? \
Library adalah kode yang digunakan oleh pengembang untuk memperluas fungsionalitas aplikasi mereka, sementara dependency adalah komponen eksternal yang diperlukan untuk menjalankan perangkat lunak tersebut.

2. Apa itu Maven? Mengapa kita menggunakan Maven? Apakah ada alternatif dari Maven? \
Maven adalah salah satu alat manajemen proyek yang digunakan dalam pengembangan perangkat lunak berbasis Java. Ini adalah alat open-source yang dikembangkan oleh Apache Software Foundation. \
Alasan penggunaan Maven:
- Manajemen dependensi: Maven memungkinkan pengembang untuk dengan mudah mengelola dan menyatakan dependensi proyek, yang adalah komponen atau pustaka eksternal yang dibutuhkan oleh proyek.
- Proyek yang terstruktur: Hal ini memudahkan pengembang untuk mengatur kode dengan cara yang konsisten dan mudah dipahami karena mencakup kode sumber, sumber daya, tes, dan dokumen proyek.
- Manajemen proyek: Maven memungkinkan pengembang untuk mengelola proyek secara terpusat melalui file konfigurasi pom.xml (Project Object Model) yang mendefinisikan konfigurasi proyek, dependensi, dan tugas-tugas yang akan dijalankan.

Alternatif Maven --> Gradle \
Gradle adalah alat manajemen proyek lain yang semakin populer dalam komunitas pengembangan Java. Ini menggunakan DSL (Domain Specific Language) berbasis Groovy atau Kotlin untuk mendefinisikan struktur proyek dan tugas-tugas. Gradle memiliki fleksibilitas yang tinggi dan dapat digunakan untuk berbagai jenis proyek, bukan hanya Java.

3. Jelaskan bagaimana alur ketika kita menjalankan http://localhost:8080/celsius-converter/90 sampai dengan muncul keluarannya di browser!


4. Selain untuk pengembangan web, apa saja yang bisa dikembangkan dengan Spring framework?
- Aplikasi Berbasis Mikroservis: Spring Boot, yang merupakan bagian dari ekosistem Spring, adalah pilihan yang sangat populer untuk mengembangkan mikroservis. Dengan Spring Boot, Anda dapat dengan cepat membuat dan mengelola berbagai mikroservis yang menjalankan fungsi-fungsi terpisah dalam aplikasi yang lebih besar. 
- Aplikasi Berbasis REST API: Spring Framework menyediakan dukungan yang kuat untuk mengembangkan RESTful API. Anda dapat menggunakan Spring MVC atau Spring WebFlux (untuk pemrosesan non-blocking) untuk membuat API yang efisien dan dapat diandalkan. 
- Aplikasi Berbasis Batch: Spring Batch adalah subproyek Spring yang menyediakan kerangka kerja untuk mengembangkan aplikasi pemrosesan batch. Ini cocok untuk tugas-tugas yang memerlukan pengolahan data besar dalam batch, seperti pengimporan data, pengolahan transaksi, atau pelaporan.

5. Apa perbedaan dari @RequestParam dan @PathVariable? Kapan sebaiknya menggunakan @RequestParam atau @PathVariable?
@RequestParam digunakan untuk mengakses nilai melalui query parameter, sedangkan @PathVariable digunakan untuk mengakses nilai dari template URI.

### What I did not understand
(tuliskan apa saja yang kurang Anda mengerti, Anda dapat mencentang apabila Anda sudah mengerti dikemudian hari, dan tambahkan tulisan yang membuat Anda mengerti)
- [ ] Kenapa namanya APAP?
