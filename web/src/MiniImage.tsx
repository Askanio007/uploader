import AvatarEditor from "react-avatar-editor";
import React from "react";

interface MiniImageProps {
    source:any,
    refFunc:any
}
const MiniImage = (props:MiniImageProps) => (
    <AvatarEditor
        image={props.source}
        ref={props.refFunc}
        width={100}
        height={100}
        border={50}
        color={[255, 255, 255, 0.6]}
        scale={1.1}
        rotate={0}
    />
);

export default MiniImage;