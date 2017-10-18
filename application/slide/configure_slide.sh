
install_dir=$(pwd)

echo Please provide path to java:
read java_dir

if [ -e "$java_dir"/java ]
then
	"$java_dir"/java -jar configure_slide.jar "$install_dir" "$java_dir" "UNIX"
else
	echo "Could not find java in $java_dir. Please ensure the path to java is correct."
fi

