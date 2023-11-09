#!/bin/bash

# create five text files
create_files() {
    for i in {1..5}; do
        touch "file$i.txt"
        echo "Hello world" > "file$i.txt"
        echo "Created file$i.txt"
    done
}

# replace "world" with "bash" text files
replace_text() {
    for i in {1..5}; do
        if [[ -f "file$i.txt" ]]; then
            sed -i 's/world/bash/g' "file$i.txt"
            echo "Replaced 'world' with 'bash' in file$i.txt"
        fi
    done
}

# menu
PS3="Select an option: "
options=("Create five text files with 'Hello world'" "Replace 'world' with 'bash' in all text files" "Quit")

select option in "${options[@]}"; do
    case $REPLY in
        1)
            create_files
            ;;
        2)
            replace_text
            ;;
        3)
=            exit 0
            ;;
        *)
            echo "Invalid choice"
            ;;
    esac
done
