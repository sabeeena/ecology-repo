import {useState} from 'react';
import {Card,Form,Button,Alert} from 'react-bootstrap';
import {Link,useNavigate} from 'react-router-dom';
import api from '../api/axiosClient';

export default function Register(){
    const nav=useNavigate();
    const [f,set]=useState({username:'',email:'',password:''});
    const [ok,setOk]=useState(false),[err,setErr]=useState('');

    const reg=async e=>{
        e.preventDefault(); setErr('');
        try{
            await api.post('/auth/register',f);
            setOk(true); setTimeout(()=>nav('/login'),1500);
        }catch(ex){ setErr('Ошибка регистрации'); }
    };

    return(
        <Card className="mx-auto" style={{maxWidth:420}}>
            <Card.Body>
                <h4 className="text-center mb-3">Регистрация</h4>
                {ok && <Alert variant="success">Успешно! Перенаправление…</Alert>}
                {err && <Alert variant="danger">{err}</Alert>}

                <Form onSubmit={reg}>
                    <Form.Control className="mb-2" placeholder="Имя пользователя"
                                  value={f.username} onChange={e=>set({...f,username:e.target.value})}/>
                    <Form.Control className="mb-2" placeholder="E-mail"
                                  value={f.email} onChange={e=>set({...f,email:e.target.value})}/>
                    <Form.Control className="mb-3" type="password" placeholder="Пароль"
                                  value={f.password} onChange={e=>set({...f,password:e.target.value})}/>
                    <Button type="submit" className="btn-eco w-100">Создать аккаунт</Button>
                </Form>
                <hr/>
                <small>Уже есть аккаунт? <Link to="/login">Войти</Link></small>
            </Card.Body>
        </Card>
    );
}
