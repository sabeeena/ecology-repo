import {useEffect,useState} from 'react';
import {Form,Button,Alert,Spinner} from 'react-bootstrap';
import api from '../api/axiosClient';

export default function Profile(){
    const [u,setU]=useState(null), [msg,setMsg]=useState('');

    useEffect(()=>{
        api.get('/profile').then(r=>setU(r.data));
    },[]);

    const save=()=> api.patch('/profile',u)
        .then(()=>setMsg('Сохранено'))
        .catch(()=>setMsg('Ошибка'));

    const deactivate=()=> api.delete('/profile')
        .then(()=>{localStorage.clear(); window.location='/login';});

    if(!u) return <Spinner/>;

    return<>
        <h3>Профиль</h3>
        {msg && <Alert>{msg}</Alert>}
        <Form>
            <Form.Control className="mb-2" value={u.firstName||''}
                          onChange={e=>setU({...u,firstName:e.target.value})}/>
            <Form.Control className="mb-2" value={u.lastName||''}
                          onChange={e=>setU({...u,lastName:e.target.value})}/>
            <Form.Control className="mb-2" value={u.email||''}
                          onChange={e=>setU({...u,email:e.target.value})}/>
            <Button className="btn-eco me-2" onClick={save}>Сохранить</Button>
            <Button variant="outline-danger" onClick={deactivate}>Деактивировать</Button>
        </Form>
    </>;
}
