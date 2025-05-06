import {useEffect, useState} from 'react';
import {Form, Button, Card, Row, Col, Alert, Spinner, Container} from 'react-bootstrap';
import {useNavigate} from 'react-router-dom';
import api from '../api/axiosClient';
import ConfirmModal from '../components/ConfirmModal';

export default function Profile({ setToken, setRole }) {
    const nav = useNavigate();

    const [user, setUser]       = useState(null);
    const [edit, setEdit]       = useState(false);
    const [saveLoad, setSaveLoad] = useState(false);
    const [err,  setErr]        = useState('');
    const [ask, setAsk]         = useState(false);

    useEffect(() => {
        api.get('/profile')
            .then(r => setUser(r.data))
            .catch(() => setErr('Не удалось загрузить профиль'));
    }, []);

    const change = (key, val) => setUser({...user, [key]: val});

    const save = async () => {
        setSaveLoad(true); setErr('');
        try {
            const patch = {
                firstName: user.firstName,
                lastName : user.lastName,
                email    : user.email,
                phoneNumber: user.phoneNumber
            };
            const {data} = await api.patch('/profile', patch);
            setUser(data); setEdit(false);
        } catch {
            setErr('Ошибка сохранения данных');
        }
        setSaveLoad(false);
    };

    const deactivate = async () => {
        try {
            await api.delete('/profile');
            localStorage.clear();
            setToken(null);
            setRole(null);
            nav('/login');
        } catch {
            setErr('Не удалось деактивировать аккаунт');
        }
    };

    if (!user) return <Spinner />;

    return (
        <Container className="py-4">
            <h3 className="mb-3">Профиль пользователя</h3>

            {err && <Alert variant="danger">{err}</Alert>}

            <Card className="shadow-sm">
                <Card.Body>
                    <Form>
                        <Row className="g-3">
                            <Col md={6}>
                                <Form.Label>Имя</Form.Label>
                                <Form.Control
                                    disabled={!edit}
                                    value={user.firstName ?? ''}
                                    onChange={e => change('firstName', e.target.value)}
                                />
                            </Col>

                            <Col md={6}>
                                <Form.Label>Фамилия</Form.Label>
                                <Form.Control
                                    disabled={!edit}
                                    value={user.lastName ?? ''}
                                    onChange={e => change('lastName', e.target.value)}
                                />
                            </Col>

                            <Col md={6}>
                                <Form.Label>E-mail</Form.Label>
                                <Form.Control
                                    disabled={!edit}
                                    type="email"
                                    value={user.email ?? ''}
                                    onChange={e => change('email', e.target.value)}
                                />
                            </Col>

                            <Col md={6}>
                                <Form.Label>Телефон</Form.Label>
                                <Form.Control
                                    disabled={!edit}
                                    value={user.phoneNumber ?? ''}
                                    onChange={e => change('phoneNumber', e.target.value)}
                                />
                            </Col>

                            <Col md={6}>
                                <Form.Label>Логин (username)</Form.Label>
                                <Form.Control disabled value={user.username}/>
                            </Col>

                            <Col md={6}>
                                <Form.Label>Роль</Form.Label>
                                <Form.Control disabled value={user.role?.name ?? ''}/>
                            </Col>
                        </Row>

                        <div className="d-flex gap-2 mt-4">
                            {!edit && (
                                <Button variant="success" onClick={() => setEdit(true)}>
                                    Редактировать
                                </Button>
                            )}

                            {edit && (
                                <>
                                    <Button variant="success" onClick={save} disabled={saveLoad}>
                                        {saveLoad ? 'Сохраняю…' : 'Сохранить'}
                                    </Button>
                                    <Button variant="secondary" onClick={() => setEdit(false)}>
                                        Отменить
                                    </Button>
                                </>
                            )}

                            <Button
                                variant="outline-danger"
                                className="ms-auto"
                                onClick={() => setAsk(true)}
                            >
                                Деактивировать аккаунт
                            </Button>
                        </div>
                    </Form>
                </Card.Body>
            </Card>

            <ConfirmModal
                show={ask}
                onHide={() => setAsk(false)}
                onConfirm={deactivate}
                title="Деактивировать аккаунт?"
                body="Ваш профиль будет помечен как неактивный, а доступ к системе прекращён."
            />
        </Container>
    );
}
