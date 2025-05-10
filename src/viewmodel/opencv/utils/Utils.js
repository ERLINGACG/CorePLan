class Utils {
    static openImage(e) {
        let imageUrl = null;
        const file = e.target.files[0];
        if (!file || !file.type.includes('image/')) return;

        const reader = new FileReader();
        reader.onload = (e) => {
           imageUrl = e.target.result;
        };
        reader.readAsDataURL(file);
        return imageUrl;
    }
}
export default Utils;