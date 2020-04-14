import React, {ChangeEvent} from "react";
import {Button, TextField} from "@material-ui/core";
import UploadService, {ImageSourceType} from "./UploadService";
import MiniImage from "./MiniImage";

interface LoadFromStringState {
    isUpload: boolean
    list: string[]
    editors: any[]
}
interface LoadFromStringProps {
    typeSource: ImageSourceType,
    title: string
}
export class LoadFromString extends React.Component<LoadFromStringProps, LoadFromStringState> {

    state: LoadFromStringState = {
        isUpload: false,
        list: [],
        editors: []
    };

    handleUpload = () => {
        this.setState({
            isUpload: true,
            list: this.state.list,
            editors: this.state.editors
        })
    }

    handleSave = async () => {
        const files:File[] = await Promise.all(this.state.editors.map(async (editor:any) => {
            return await fetch(editor.getImageScaledToCanvas().toDataURL())
                .then(res => res.blob())
                .then(blob => {
                    return new File([blob], "");
                })
        }));
        await UploadService.saveMiniImage(files);
        await UploadService.saveFromString(this.state.list, this.props.typeSource);
    }

    updateString = (index:number, newValue:string) => {
        let list:string[] = this.state.list;
        list[index] = newValue;
        this.setState({list: list});
    }

    addField = () => {
        let list:string[] = this.state.list;
        list.push("");
        this.setState({list: list})
    }

    setEditorRef = (editor:any) => {
        const { list, isUpload } = this.state;
        let editors = this.state.editors;
        editors.push(editor);
        this.setState({
            isUpload: isUpload,
            list: list,
            editors: editors
        })
    }

    render() {
        const { list, isUpload } = this.state;
        const { title } = this.props;
        let inputs = list.map((str:string, index:number) => {
            return (<TextField id="outlined-basic"
                               label={title}
                               variant="outlined"
                               style={{marginBottom: 10, width: "100%"}}
                               value={str}
                               onChange={(event: ChangeEvent<HTMLInputElement>) => this.updateString(index, event.currentTarget.value)} />)
        })

        let images:any[] = [];
        if (isUpload) {
            images = Array.from(list).map((f:string) => (<MiniImage source={f} refFunc={this.setEditorRef}/>));
        }
        return (
            <div>
                {inputs}
                <Button variant="contained" color="primary" style={{marginRight: 5}} onClick={this.addField}>Add field</Button>
                <Button variant="contained" color="primary" disabled={inputs.length == 0} style={{marginRight: 5}}  onClick={this.handleUpload}>Upload</Button>
                <Button variant="contained" color="primary" disabled={!isUpload} onClick={this.handleSave}>Save Mini</Button>
                <div>{images}</div>
            </div>
        )
    }
    
}