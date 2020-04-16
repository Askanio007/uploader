import axios, {AxiosRequestConfig} from "axios";

const url = '/api/v1/images';

class UploadService {

    public saveFromFile(file:FileList, callback?:any):void {
        let fileFormData = new FormData();
        Array.from(file).forEach(f => fileFormData.append("files", f));
        let options = {
            headers: {'Content-Type': 'multipart/form-data'}
        };
        UploadService.post(fileFormData, options, callback);
    }

    public saveMiniImage(file:File[], callback?:any):void {
        let fileFormData = new FormData();
        file.forEach(f => fileFormData.append("files", f));
        let options = {
            headers: {'Content-Type': 'multipart/form-data'}
        };
        UploadService.post(fileFormData, options, callback);
    }

    public saveFromString(strings:string[], type:ImageSourceType, callback?:any):void {
        let options = {
            headers: {'Content-Type': 'application/json'}
        };
        let data = {
            "type": type,
            "codes": strings
        }
        UploadService.post(data, options, callback)
    }

    private static post(data?:any, config?:AxiosRequestConfig, callback?:any): void {
        axios.create().post(url, data, config)
            .then(callback)
            .catch((err:any) => {
                console.log("Failed api response:" + err);
            });
    }
}

export enum ImageSourceType {
    URL,BASE64
}

export default new UploadService();