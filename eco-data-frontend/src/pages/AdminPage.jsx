import { useEffect, useState } from 'react';
import { Container, Button, Alert, Spinner } from 'react-bootstrap';
import {
    fetchRoles, searchUsers, deleteUser
} from '../api/adminApi';
import AdminUserFilter from '../components/AdminUserFilter';
import AdminUserTable  from '../components/AdminUserTable';
import UserFormModal   from '../components/UserFormModal';
import ConfirmModal    from '../components/ConfirmModal';

export default function AdminPage() {
    const [roles,  setRoles]  = useState([]);
    const [users,  setUsers]  = useState([]);
    const [filter, setFilter] = useState({});
    const [loading,setLoading]= useState(false);
    const [error,  setError]  = useState('');

    const [editUser,setEditUser]  = useState(null);
    const [showForm,setShowForm]  = useState(false);
    const [delUser,setDelUser]    = useState(null);
    const [showDel,setShowDel]    = useState(false);

    useEffect(()=>{ fetchRoles().then(r=>setRoles(r.data)); },[]);

    const loadUsers = async ()=> {
        setLoading(true); setError('');
        try{
            const {data} = await searchUsers(filter);
            setUsers(data.content);
        }catch{ setError('Ошибка загрузки'); }
        setLoading(false);
    };

    useEffect(()=>{ loadUsers(); },[]);

    return (
        <Container className="py-4">
            <h3 className="mb-3">Админ-панель: Пользователи</h3>

            <AdminUserFilter roles={roles}
                             onChange={setFilter}
                             onSearch={loadUsers}/>

            <Button className="mb-3 btn-eco"
                    onClick={()=>{ setEditUser(null); setShowForm(true); }}>
                ➕ Создать
            </Button>

            {error && <Alert variant="danger">{error}</Alert>}
            {loading ? <Spinner/> :
                <AdminUserTable
                    users={users}
                    onEdit={u=>{ setEditUser(u); setShowForm(true); }}
                    onDelete={u=>{ setDelUser(u); setShowDel(true); }}/>
            }

            <UserFormModal show={showForm} user={editUser} roles={roles}
                           onHide={()=>setShowForm(false)} onSave={loadUsers}/>
            <ConfirmModal  show={showDel}
                           text={`Удалить пользователя ${delUser?.username}?`}
                           onConfirm={async()=>{
                               await deleteUser(delUser.id); setShowDel(false); loadUsers();
                           }}
                           onHide={()=>setShowDel(false)}/>
        </Container>
    );
}