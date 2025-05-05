import { useEffect, useState } from 'react';
import { Modal, Button, Form, Row, Col } from 'react-bootstrap';
import { createUser, updateUser } from '../api/adminApi';

export default function UserFormModal({ show, user, roles, onHide, onSave }) {
    const [u,setU] = useState({});

    useEffect(()=> setU(user ?? {
        firstName:'',lastName:'',username:'',email:'',phoneNumber:'',
        role:(roles[0]?.code)||'',isActive:true,password:''
    }), [user,roles]);

    const change=(k,v)=>setU({...u,[k]:v});

    const submit=async()=>{
        if(u.id) await updateUser(u.id,u); else await createUser(u);
        onSave(); onHide();
    };

    return (
        <Modal show={show} onHide={onHide} size="lg" backdrop="static">
            <Modal.Header closeButton><Modal.Title>{u.id?'Редактирование':'Создание'} пользователя</Modal.Title></Modal.Header>
            <Modal.Body>
                <Form>
                    <Row className="g-2">
                        <Col md={6}><Form.Control placeholder="Имя"  value={u.firstName}
                                                  onChange={e=>change('firstName',e.target.value)}/></Col>
                        <Col md={6}><Form.Control placeholder="Фамилия" value={u.lastName}
                                                  onChange={e=>change('lastName',e.target.value)}/></Col>
                        <Col md={4}><Form.Control placeholder="Username" value={u.username}
                                                  onChange={e=>change('username',e.target.value)}/></Col>
                        <Col md={4}><Form.Control placeholder="Email" value={u.email}
                                                  onChange={e=>change('email',e.target.value)}/></Col>
                        <Col md={4}><Form.Control placeholder="Телефон" value={u.phoneNumber}
                                                  onChange={e=>change('phoneNumber',e.target.value)}/></Col>
                        <Col md={4}>
                            <Form.Select value={u.role?.code||u.role}
                                         onChange={e=>change('role', e.target.value)}>
                                {roles.map(r=> <option key={r.code} value={r.code}>{r.name}</option>)}
                            </Form.Select>
                        </Col>
                        <Col md={4}>
                            <Form.Select value={u.isActive}
                                         onChange={e=>change('isActive', e.target.value==='true')}>
                                <option value="true">Активен</option>
                                <option value="false">Блокирован</option>
                            </Form.Select>
                        </Col>
                        {!u.id && <Col md={4}>
                            <Form.Control placeholder="Пароль"
                                          type="password"
                                          value={u.password}
                                          onChange={e=>change('password',e.target.value)}/>
                        </Col>}
                    </Row>
                </Form>
            </Modal.Body>
            <Modal.Footer>
                <Button variant="secondary" onClick={onHide}>Отмена</Button>
                <Button variant="success" onClick={submit}>Сохранить</Button>
            </Modal.Footer>
        </Modal>
    );
}