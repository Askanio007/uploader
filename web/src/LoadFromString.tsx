import React, {ChangeEvent} from "react";
import {Button, TextField} from "@material-ui/core";
import UploadService, {ImageSourceType} from "./service/UploadService";
import {LoadFromMini} from "./components/LoadFromMini";
import UploadResult from "./components/UploadResult";

interface LoadFromStringState {
    result: string[]
    error: string | null
    isUpload: boolean
    list: string[]
}
interface LoadFromStringProps {
    typeSource: ImageSourceType,
    title: string
}
export class LoadFromString extends React.Component<LoadFromStringProps, LoadFromStringState> {

    state: LoadFromStringState = {
        result: [],
        error: null,
        isUpload: false,
        list: []
    };

    handleUpload = () => {
        UploadService.saveFromString(this.state.list, this.props.typeSource, (res:any) => {
            if (res.data.success) {
                this.setState({
                    result: res.data.data,
                    error: null,
                    isUpload: true,
                    list: this.state.list
                })
            } else {
                this.setState({
                    error: res.data.error,
                    isUpload: res.data.error == null,
                    list: this.state.list
                })
            }
        });

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

    render() {
        const { list, isUpload, error, result } = this.state;
        const { title } = this.props;
        let inputs = list.map((str:string, index:number) => {
            return (<TextField id="outlined-basic"
                               label={title}
                               variant="outlined"
                               style={{marginBottom: 10, width: "100%"}}
                               value={str}
                               onChange={(event: ChangeEvent<HTMLInputElement>) => this.updateString(index, event.currentTarget.value)} />)
        })

        return (
            <div>
                {inputs}
                <Button variant="contained" color="primary" style={{marginRight: 5}} onClick={this.addField}>Add field</Button>
                <Button variant="contained" color="primary" disabled={inputs.length === 0} style={{marginRight: 5}}  onClick={this.handleUpload}>Upload</Button>
                <div style={{color: 'green'}}>{isUpload ? 'Image upload successfully. Choose mini' : ''}</div>
                <div style={{color: 'red'}}>{error != null ? error : ''}</div>
                <UploadResult isMini={false} list={result} />
                {isUpload ? <LoadFromMini list={list} /> : ''}
            </div>
        )
    }
    
}