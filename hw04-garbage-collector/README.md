### Таблица запусков

|       | До оптимизации           | После оптимизации      |
|-------|--------------------------|------------------------|
| 256M  | spend msec:39117, sec:39 | spend msec:6467, sec:6 |
| 512M  | spend msec:55722, sec:55 | spend msec:5870, sec:5 |
| 768M  | spend msec:50432, sec:50 | spend msec:5278, sec:5 |
| 1024M | spend msec:55027, sec:55 | spend msec:5208, sec:5 |
| 1280M | spend msec:37072, sec:37 | spend msec:5032, sec:5 |
| 1536M | spend msec:43937, sec:43 | spend msec:5098, sec:5 |
| 1792M | spend msec:45185, sec:45 | spend msec:5106, sec:5 |
| 2048M | spend msec:45879, sec:45 | spend msec:5390, sec:5 |

Оптимизация заключалась в замене Integer на int во всех классах.
