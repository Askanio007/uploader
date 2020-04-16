import React, {ChangeEvent} from "react";
import {Button} from "@material-ui/core";
import UploadService from "./service/UploadService";
import UploadResult from "./components/UploadResult";
import {LoadFromMini} from "./components/LoadFromMini";

interface LoadFromFileState {
    result: any[]
    isUpload: boolean
    error: string | null
    list: FileList | null
}
export default class LoadFromFile extends React.Component<any, LoadFromFileState> {

    state: LoadFromFileState = {
        result:[],
        isUpload: false,
        error: null,
        list: null
    }

    handleUpload = (event: ChangeEvent<HTMLInputElement>) => {
        if (event.target.files) {
            const files:FileList = event.target.files;
            UploadService.saveFromFile(files, (res: any) => {
                if (res.data.success) {
                    this.setState({
                        result: res.data.data,
                        isUpload: true,
                        error: res.data.error,
                        list: files
                    });
                } else {
                    this.setState({
                        result: [],
                        isUpload: false,
                        error: res.data.error,
                        list: files
                    });
                }

            });
        }
    }

    render() {
        const { list, error, isUpload, result } = this.state;
        let fileList:File[] = []
        if (list != null) {
            fileList = Array.from(list).map((f:File) => f);
        }
        return (
            <form>
                <div>
                    <Button variant="contained" color="primary" component="label">
                        Upload from file
                        <input type="file" multiple style={{ display: "none" }} onChange={this.handleUpload}/>
                    </Button>
                </div>
                <div style={{color: 'green'}}>{isUpload ? 'Image upload successfully. Choose mini' : ''}</div>
                <div style={{color: 'red'}}>{error != null ? error : ''}</div>
                <UploadResult isMini={false} list={result} />
                {isUpload ? <LoadFromMini list={fileList} /> : ''}
            </form>
        )
    }
}