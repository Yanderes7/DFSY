package cn.synu;

import cn.synu.domain.Teacher;
import cn.synu.mapper.TeacherMapper;
import cn.synu.qo.XHQueryObject;
import cn.synu.service.CourseService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class SynuXhApplicationTests {

    @Autowired
    private CourseService courseService;
    @Autowired
    private TeacherMapper teacherMapper;

    @Test
    void contextLoads() {
        XHQueryObject qo = new XHQueryObject();
        qo.setCurrentPage(1);
        qo.setPageSize(5);
        System.out.println(courseService.query(qo));
    }

    @Test
    void test() {
        XHQueryObject qo = new XHQueryObject();
        List<Teacher> teachers = teacherMapper.selectForList(qo);
        System.out.println(teachers);
    }
}
