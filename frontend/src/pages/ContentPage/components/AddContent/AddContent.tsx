import Modal from "../../../../components/Modal/Modal"
import type { AddContentProps } from "./AddContent.types"

const AddContent = ({ onClose }: AddContentProps) => {
    return (
        <Modal closeModal={onClose}>
            <div>AddContent</div>
        </Modal>
    )
}

export default AddContent