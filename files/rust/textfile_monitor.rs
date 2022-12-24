use std::fs;
use std::time::Duration;

fn main() {
    let path = "path";
    let mut last_modified = fs::metadata(path).unwrap().modified().unwrap();

    loop {
        std::thread::sleep(Duration::from_secs(3));

        let modified = fs::metadata(path).unwrap().modified().unwrap();
        if modified > last_modified {
            println!("the file has been updated!");
            last_modified = modified;
        }
    }
}
