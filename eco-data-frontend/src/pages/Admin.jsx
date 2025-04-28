import {useEffect,useState} from 'react';
import {Table,Button,Form,Spinner,Alert} from 'react-bootstrap';
import api from '../api/axiosClient';

export default function Admin(){
    const [users,set]=useState([]),[loading,setL]=useState(false),[err,setErr]=useState('');

    const load=()=>{ setL(true); api.post('/users/info',{})      // пустой фильтр
        .then(r=>set(r.data.content||r.data))
        .catch(()=>setErr('Ошибка'))
        .finally(()=>setL(false)); };

    useEffect(load,[]);

    return<>
        <h3>Админ-панель: пользователи</h3>
        <Button className="btn-eco mb-2" onClick={load}>Обновить</Button>
        {err && <Alert variant="danger">{err}</Alert>}
        {loading && <Spinner/>}
        <Table striped bordered size="sm">
            <thead><tr><th>ID</th><th>Логин</th><th>Имя</th><th>Email</th><th>Роль</th></tr></thead>
            <tbody>
            {users.map(u=>
                <tr key={u.id}>
                    <td>{u.id}</td><td>{u.username}</td>
                    <td>{u.fullName}</td><td>{u.email}</td><td>{u.role}</td>
                </tr>)}
            </tbody>
        </Table>
    </>;
}
