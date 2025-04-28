import {useState} from 'react';
import {Card,Form,Button,Alert} from 'react-bootstrap';
import {useNavigate,Link} from 'react-router-dom';
import api from '../api/axiosClient';

export default function Login(){
    const nav=useNavigate();
    const [form,set]=useState({username:'',password:''});
    const [err,setErr]=useState('');

    const submit= async e=>{
        e.preventDefault(); setErr('');
        try{
            const {data}=await api.post('/auth/login',null,
                {params:{username:form.username,password:form.password}});
            localStorage.setItem('token',data.token);
            localStorage.setItem('role', data.role);
            nav('/');
        }catch{ setErr('Неверный логин или пароль'); }
    };

    return(
        <Card className="mx-auto" style={{maxWidth:380}}>
            <Card.Body>
                <h4 className="mb-3 text-center">Вход</h4>
                {err && <Alert variant="danger">{err}</Alert>}
                <Form onSubmit={submit}>
                    <Form.Control className="mb-2" placeholder="Логин"
                                  value={form.username}
                                  onChange={e=>set({...form,username:e.target.value})}/>
                    <Form.Control className="mb-3" placeholder="Пароль" type="password"
                                  value={form.password}
                                  onChange={e=>set({...form,password:e.target.value})}/>
                    <Button type="submit" className="btn-eco w-100">Войти</Button>
                </Form>
                <hr/>
                <small>Нет аккаунта? <Link to="/register">Регистрация</Link></small>
            </Card.Body>
        </Card>
    );
}
