#include <iostream>
#include <cstdlib>
#include <string>

int main(int argc, char* argv[]) {
    if (argc != 2) {
        std::cerr << "Usage: dl <URL>" << std::endl;
        return 1;
    }

    std::string url = argv[1];
    std::string command = "yt-dlp -x --audio-format mp3 --embed-metadata --add-metadata --metadata-from-title \"%(artist)s - %(title)s\" " + url;
    int result = system(command.c_str());

    if (result != 0) {
        std::cerr << "Error: Failed to download and process the URL." << std::endl;
        return 1;
    }

    std::cout << "Download and conversion successful." << std::endl;
    return 0;
}
