package com.cdut;

import com.cdut.dao.mysql.page.PageRepository;
import com.cdut.entity.page.PageInfoPO;
import com.cdut.entity.page.PicturePO;
import com.cdut.enums.CommonStatusEnum;
import com.cdut.util.IdService;
import com.cdut.vo.PageInfoRespVO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MeetRoomApplicationTests {

	@Autowired
	private PageRepository pageRepository;

	@Autowired
	private IdService idService;

	@Autowired
	private JavaMailSender javaMailSender;

	@Test
	public void contextLoads() {

		/*PageInfoPO po = new PageInfoPO();
		po.setCommonStatusEnum(CommonStatusEnum.ENABLE);
		po.setTitle("青年地学社");
		po.setId(idService.nextId());
		po.setSubTitle("english name");
		pageRepository.save(po);*/
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom("dominant_wangcheng@163.com");
		message.setTo("453451180@qq.com");
		message.setSubject("这是测试邮件");
		message.setText("qiang");
		javaMailSender.send(message);
	}

}
