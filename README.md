# FP's Search Engine 🔍

A local search engine built from scratch in **Java**, designed to index and query hundreds of books from Project Gutenberg. This project explores the core fundamentals of Information Retrieval without relying on external libraries.

# Demo of the Search Engine 

https://github.com/user-attachments/assets/25380027-aa1e-4a2a-a7ff-976568b9cf41

# 🧠 Core Algorithms & Data Structures
This engine goes beyond simple text matching by implementing real-world search engine logic:
* **Inverted Index:** Data is structured using nested Maps to ensure instantaneous search times (O(1)) regardless of the text volume loaded in RAM.
* **TF-IDF Ranking:** Results are not random, they are mathematically sorted by relevance using *Term Frequency* and *Inverse Document Frequency* logarithmic calculations.
* **Natural Language Processing (NLP):**
  * **Stop-Words Filter:** Automatic removal of common English words to optimize memory usage and improve mathematical precision.
  * **Porter Stemmer:** Integration of M.Porter's standard algorithm (1980) to reduce words to their root form (e.g., *vampires* -> *vampir*), significantly improving search recall.
* **Boolean Search (AND):** Support for multi-word queries with strict document set intersection.

# 📂 Recursive Navigation & Smart Filtering
The engine doesn't just read a flat directory, it acts as a crawler for your local file system:
* **Labyrinth Search (Recursion):** The file reader uses a recursive algorithm to dive into deep, nested directory trees. No matter how complex the folder structure is, the engine explores every single branch and sub-folder to find hidden archives.
* **Targeted Extraction:** It strictly targets Project Gutenberg's specific zip formats (like `-0.zip`), ignoring irrelevant system files.
* **Language & Format Filtering:** The algorithm automatically unzips the archives in-memory, extracting only valid `.txt` files and specifically filtering for English literature texts, seamlessly skipping incompatible or foreign formats.

# 💻 GUI & Features
* Intuitive Graphical User Interface built with **Java Swing**.
* **Instant Extraction:** Users can open and read the full text of any selected book directly from the UI. The engine handles the `.zip` archive extraction on the fly using temporary files, keeping the local storage clean.

# 🚀 How to Run
1. Clone this repository.
2. Download a set of UTF-8 `.txt` files archived in `.zip` format from Project Gutenberg and place them in a local folder.
3. Update the folder path in `MotoreMain.java`.
4. Compile and run. No external dependencies or libraries required (100% Core Java).

------------
Federico Pascucci

* Data provided by Project Gutenberg <3 
