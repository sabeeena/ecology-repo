import {useEffect, useState} from 'react';
import {Row, Col, Form, Button} from 'react-bootstrap';
import dayjs from 'dayjs';
import api from '../api/axiosClient';

/**
 * Фильтр для дашборда:
 *  • показатель
 *  • город
 *  • период (от / до)
 *
 * onSearch(filter) — колбэк, вызывается при нажатии кнопки.
 */
export default function DashboardFilter({onSearch}) {
    /** Возможные показатели (совпадают с названиями столбцов БД) */
    const pollutants = [
        'aqi','pm2_5','pm10','o3','co','no2','so2','nh3'
    ];

    /** Список городов, подтягиваем с backend (/api/cities) */
    const [cities, setCities] = useState([]);

    /** Локальное состояние фильтра */
    const [filter, setFilter] = useState({
        pollutant : 'aqi',
        cityId    : '',
        from      : dayjs().subtract(7, 'day').startOf('hour').toISOString(),
        to        : dayjs().endOf('hour').toISOString()
    });

    /** При первой отрисовке – загрузить список городов */
    useEffect(()=>{
        api.get('/reference/cities').then(r => setCities(r.data))
            .catch(()=> setCities([]));
    },[]);

    /** Общий обработчик изменения полей */
    const change = (key,val) => setFilter({...filter,[key]:val});

    /** Нажатие «Показать» */
    const submit = () => onSearch(filter);

    return (
        <Form className="mb-3">
            <Row className="g-2">
                <Col md={3}>
                    <Form.Label>Показатель</Form.Label>
                    <Form.Select value={filter.pollutant}
                                 onChange={e=>change('pollutant', e.target.value)}>
                        {pollutants.map(p=> <option key={p} value={p}>{p.toUpperCase()}</option>)}
                    </Form.Select>
                </Col>

                <Col md={3}>
                    <Form.Label>Город</Form.Label>
                    <Form.Select value={filter.cityId}
                                 onChange={e=>change('cityId', e.target.value || '')}>
                        <option value="">– Все города –</option>
                        {cities.map(c=> <option key={c.id} value={c.id}>{c.name}</option>)}
                    </Form.Select>
                </Col>

                <Col md={2}>
                    <Form.Label>Дата начала</Form.Label>
                    <Form.Control type="datetime-local"
                                  defaultValue={filter.from.slice(0,16)}
                                  onChange={e=>change('from', new Date(e.target.value).toISOString())}/>
                </Col>

                <Col md={2}>
                    <Form.Label>Дата конца</Form.Label>
                    <Form.Control type="datetime-local"
                                  defaultValue={filter.to.slice(0,16)}
                                  onChange={e=>change('to', new Date(e.target.value).toISOString())}/>
                </Col>

                <Col md={2} className="d-flex align-items-end">
                    <Form.Label>&nbsp;</Form.Label>
                    <Button className="w-100 shadow-sm" variant="success" onClick={submit}>
                        Показать
                    </Button>
                </Col>
            </Row>
        </Form>
    );
}
