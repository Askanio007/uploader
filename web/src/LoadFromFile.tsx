import React, {ChangeEvent} from "react";
import {Button} from "@material-ui/core";
import UploadService from "./UploadService";
import MiniImage from "./MiniImage";

interface LoadFromFileState {
    isUpload: boolean
    isMiniUpload: boolean
    error: string | null
    list: FileList | null
    editors: any[]
}
export default class LoadFromFile extends React.Component<any, LoadFromFileState> {

    state: LoadFromFileState = {
        isUpload: false,
        isMiniUpload: false,
        error: null,
        list: null,
        editors: []
    }

    handleUpload = (event: ChangeEvent<HTMLInputElement>) => {
        if (event.target.files) {
            const files:FileList = event.target.files;
            UploadService.saveFromFile(files, (res: any) => {
                this.setState({
                    isUpload: res.data.error == null,
                    isMiniUpload: false,
                    error: res.data.error,
                    list: files,
                    editors: []
                });
            });
        }
    }

    handleMiniImage = async () => {
        const files:File[] = await Promise.all(this.state.editors.map(async (editor:any) => {
            return await fetch(editor.getImageScaledToCanvas().toDataURL())
                .then(res => res.blob())
                .then(blob => {
                    return new File([blob], "");
                })
        }));
        await UploadService.saveMiniImage(files, (res: any) => {
            this.setState({
                isUpload: false,
                isMiniUpload: res.data.error == null,
                error: res.data.error,
                list: null,
                editors: []
            });
        });
    }

    setEditorRef = (editor:any) => {
        if (editor != null) {
            let editors = this.state.editors;
            editors.push(editor);
            this.setState({
                list: this.state.list,
                editors: editors
            })
        }
    }

    render() {
        let images:any[] = [];
        const { list, error, isMiniUpload, isUpload } = this.state;
        if (list != null) {
            images = Array.from(list).map((f:File) => (<MiniImage source={f} refFunc={this.setEditorRef}/>));
        }
        return (
            <form>
                <div>
                    <Button variant="contained" color="primary" component="label">
                        Upload from file
                        <input type="file" multiple style={{ display: "none" }} onChange={this.handleUpload}/>
                    </Button>
                    <Button variant="contained" color="primary" disabled={this.state == null} onClick={this.handleMiniImage}>Save</Button>
                </div>
                <div style={{color: 'green'}}>{isUpload ? 'Image upload successfully. Choose mini' : ''}</div>
                <div style={{color: 'green'}}>{isMiniUpload ? 'Mini image upload successfully.' : ''}</div>
                <div style={{color: 'red'}}>{error != null ? error : ''}</div>
                <div>{images}</div>
            </form>
        )
    }
}