import {Card, Table, OverlayTrigger, Tooltip} from 'react-bootstrap';

export default function AqiInfoCard() {
    /* обычная tooltip-подсказка со ссылкой-источником */
    const source = (
        <Tooltip id="source-tip">
            Data taken from&nbsp;
                OpenWeather
        </Tooltip>
    );

    return (
        <Card className="mb-4 shadow-sm">
            <Card.Body>
                <Card.Title>Что такое Air Quality Index?</Card.Title>

                <Card.Text className="mb-3">
                    AQI — это интегральный показатель, который позволяет оценить
                    общее качество воздуха «одной цифрой».
                    Чем ближе значение к <span className="fw-bold">1</span>,
                    тем чище воздух; <span className="fw-bold">5</span> — «очень
                    плохой» уровень. Помимо самого индекса API возвращает
                    концентрации конкретных загрязнителей: CO, NO, NO<sub>2</sub>,
                    O<sub>3</sub>, SO<sub>2</sub>, NH<sub>3</sub>,
                    а также взвешенных частиц PM<sub>2.5</sub> и PM<sub>10</sub>.
                </Card.Text>

                {/* Таблица шкалы */}
                <div className="table-responsive">
                    <Table bordered size="sm" className="mb-0 text-center">
                        <thead className="table-light">
                        <tr>
                            <th rowSpan="2" className="align-middle">Качество</th>
                            <th rowSpan="2" className="align-middle">AQI<br/>уровень</th>
                            <th colSpan="6">ПДК* (µg/m³)</th>
                        </tr>
                        <tr>
                            <th>SO₂</th><th>NO₂</th><th>PM10</th><th>PM2.5</th><th>O₃</th><th>CO</th>
                        </tr>
                        </thead>
                        <tbody>
                        {[
                            ['Good',       1, '0-20',  '0-40',  '0-20',  '0-10',  '0-60',   '0-4400'],
                            ['Fair',       2, '20-80', '40-70', '20-50', '10-25', '60-100', '4400-9400'],
                            ['Moderate',   3, '80-250','70-150','50-100','25-50', '100-140','9400-12400'],
                            ['Poor',       4, '250-350','150-200','100-200','50-75','140-180','12400-15400'],
                            ['Very poor',  5, '≥350',  '≥200', '≥200',  '≥75',  '≥180',  '≥15400']
                        ].map(r => (
                            <tr key={r[0]}>
                                {r.map((c,i) => <td key={i}>{c}</td>)}
                            </tr>
                        ))}
                        </tbody>
                    </Table>
                    <small className="text-muted">
                        * Диапазоны усреднены из методики OpenWeather
                        (
                        <OverlayTrigger overlay={source} placement="top">
                            <span style={{cursor:'help'}}>
                                <a
                                href="https://openweathermap.org/air-pollution-index-levels"
                                target="_blank"
                                rel="noopener noreferrer"
                                className="text-success text-decoration-underline"
                            >
                                    источник
                            </a>
                            </span>
                        </OverlayTrigger>
                        )
                    </small>
                </div>
            </Card.Body>
        </Card>
    );
}
