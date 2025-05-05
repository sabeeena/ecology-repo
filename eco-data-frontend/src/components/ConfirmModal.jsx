import {Modal, Button} from 'react-bootstrap';

export default function ConfirmModal({show, onHide, onConfirm, title, body}) {
    return (
        <Modal show={show} onHide={onHide} centered>
            <Modal.Header closeButton>
                <Modal.Title>{title}</Modal.Title>
            </Modal.Header>

            <Modal.Body>{body}</Modal.Body>

            <Modal.Footer>
                <Button variant="secondary" onClick={onHide}>
                    Отмена
                </Button>
                <Button variant="danger" onClick={() => { onConfirm(); onHide(); }}>
                    Подтвердить
                </Button>
            </Modal.Footer>
        </Modal>
    );
}
