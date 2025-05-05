import { Table, Badge, Button } from 'react-bootstrap';
import { fmtDate } from '../utils/format';

export default function AdminUserTable({ users, onEdit, onDelete }) {
    return (
        <Table bordered hover responsive size="sm">
            <thead className="table-light">
            <tr>
                <th>ID</th><th>Username</th><th>ФИО</th><th>Email</th>
                <th>Телефон</th><th>Роль</th><th>Создан</th><th>Статус</th><th></th>
            </tr>
            </thead>
            <tbody>
            {users.map(u=>(
                <tr key={u.id}>
                    <td>{u.id}</td><td>{u.username}</td><td>{u.fullName}</td><td>{u.email}</td>
                    <td>{u.phoneNumber}</td><td>{u.role}</td>
                    <td>{fmtDate(u.createdAt)}</td>
                    <td>
                        <Badge bg={u.isActive?'success':'secondary'}>
                            {u.isActive?'Активен':'Блок'}
                        </Badge>
                    </td>
                    <td className="text-nowrap">
                        <Button size="sm" variant="outline-primary" onClick={()=>onEdit(u)}>✏️</Button>{' '}
                        <Button size="sm" variant="outline-danger"  onClick={()=>onDelete(u)}>🗑️</Button>
                    </td>
                </tr>
            ))}
            </tbody>
        </Table>
    );
}