import React from "react";
import {Button} from "@material-ui/core";
import MiniImage from "./MiniImage";
import UploadService from "../service/UploadService";
import UploadResult from "./UploadResult";

interface LoadFromMiniProps {
    list: any[]
}
interface LoadFromMiniState {
    isUpload: boolean
    result: any[]
    editors: any[]
}
export class LoadFromMini extends React.Component<LoadFromMiniProps, LoadFromMiniState> {

    state:LoadFromMiniState = {
        isUpload: false,
        result: [],
        editors: []
    }

    handleSave = async () => {
        const files:File[] = await Promise.all(this.state.editors.map(async (editor:any, index:number) => {
            return await fetch(editor.getImageScaledToCanvas().toDataURL())
                .then(res => res.blob())
                .then(blob => {
                    let fileName = editor.props.image.name ? editor.props.image.name : 'number' + index;
                    return new File([blob], fileName, { type: 'image/jpeg' });
                })
        }));
        await UploadService.saveMiniImage(files, (res:any) => {
            if (res.data.success) {
                this.setState({
                    isUpload: true,
                    result: res.data.data,
                    editors: []
                })
            } else {
                let error:any[] = [res.data.error];
                this.setState({
                    isUpload: true,
                    result: error,
                    editors: []
                })
            }
        });
    }

    setEditorRef = (editor:any) => {
        if (editor != null) {
            let editors = this.state.editors;
            editors.push(editor);
            this.setState({
                editors: editors
            })
        }
    }

    render() {
        const { list } = this.props;
        const { isUpload, result } = this.state;
        let images:any[] = list.map((f:string) => (<MiniImage source={f} refFunc={this.setEditorRef}/>));
        return (
            <div>
                {isUpload ? '' : <Button variant="contained" color="primary" onClick={this.handleSave}>Save Mini</Button>}
                <div>{isUpload ? <UploadResult  isMini={true} list={result}/> : images }</div>
            </div>
        )
    }
}