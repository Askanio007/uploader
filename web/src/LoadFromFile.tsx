import React, {ChangeEvent} from "react";
import {Button} from "@material-ui/core";
import UploadService from "./UploadService";
import MiniImage from "./MiniImage";

interface ILoadFromFile {
    list: FileList
    editors: any[]
}
export default class LoadFromFile extends React.Component<any, ILoadFromFile> {

    handleMiniImage = async (event: ChangeEvent<HTMLInputElement>) => {
        if (event.target.files) {
            this.setState({
                list: event.target.files,
                editors: []
            })
            await UploadService.saveFromFile(event.target.files);
        }
    }

    handelUpload = async () => {
        const files:File[] = await Promise.all(this.state.editors.map(async (editor:any) => {
            return await fetch(editor.getImageScaledToCanvas().toDataURL())
                .then(res => res.blob())
                .then(blob => {
                    return new File([blob], "");
                })
        }));
        await UploadService.saveMiniImage(files);
    }

    setEditorRef = (editor:any) => {
        let editors = this.state.editors;
        editors.push(editor);
        this.setState({
            list: this.state.list,
            editors: editors
        })
    }

    render() {
        let images:any[] = [];
        if (this.state != null) {
            const { list } = this.state;
            images = Array.from(list).map((f:File) => (<MiniImage source={f} refFunc={this.setEditorRef}/>));
        }
        return (
            <form>
                <div>
                    <Button variant="contained" color="primary" component="label">
                        Upload from file
                        <input type="file" multiple style={{ display: "none" }} onChange={this.handleMiniImage}/>
                    </Button>
                    <Button variant="contained" color="primary" disabled={this.state == null} onClick={this.handelUpload}>Save</Button>
                </div>
                {images}
            </form>
        )
    }
}