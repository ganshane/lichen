package lichen.creeper.core.components;

import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.EventContext;
import org.apache.tapestry5.Link;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.annotations.BeginRender;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.QPageRequest;

/**
 * 分页组件
 * @author shen
 *
 * @param <T>
 */
public class Pagination<T> {
	
	@SuppressWarnings("unused")
	private Logger logger= LoggerFactory.getLogger(Pagination.class);
	
	@Parameter
	private Page<T> page;
	
	@Parameter
	private Object[] context;
	
	//当前选中页码
	private int selectedPage;
	
	private int pageSize = 1;
	
	@Inject
    private ComponentResources resources;
	
	private EventContext eventContext;
	
	@BeginRender
	void begin(MarkupWriter writer){
		writer.element("ul", "class", "pagination","bbb","ccc");
		writeLi(writer);
		writer.write("结果共"+page.getTotalElements()+"条");
		writer.end();
	}
	
	private void writeLi(MarkupWriter writer){
		//当前显示页
		int curPage = page.getNumber()+1;
		//总页数
		int pageCount = page.getTotalPages();
		//页码开始页
		int begin = curPage <= 5 ? 1 : curPage-5;
		//页码结束页
		int end = begin+9 > pageCount ? pageCount : begin+9;
		//上一页
		int prepage = curPage - 1;
		//下一页
		int nextpage = curPage + 1;
		
		//上一页逻辑
		if(curPage!=1){
			writer.element("li");
			Object[] precontext = context == null
	        ? new Object[] { prepage }
	        : updateArray(context,prepage);
			writeLink(writer,precontext,"<<");
		}else{
			writer.element("li","class","disabled");
			writeLink(writer,null,"<<");
		}
		writer.end();
		//页码逻辑
		for(int i=begin;i<=end;i++){
			if(i==curPage){
				writer.element("li","class","active");
			}else{
				writer.element("li");
			}
			Object[] newcontext = context == null
            ? new Object[] { i }
            : updateArray(context,i);
            writeLink(writer,newcontext,String.valueOf(i));
			writer.end();
		}
		//下一页逻辑
		if(curPage != pageCount){
			writer.element("li");
			Object[] nextcontext = context == null
	        ? new Object[] { nextpage }
	        : updateArray(context,nextpage);
			writeLink(writer,nextcontext,">>");
		}else{
			writer.element("li","class","disabled");
			writeLink(writer,null,">>");
		}
		writer.end();
	}
	
	private void writeLink(MarkupWriter writer,Object[] context, String text){
		Link link = resources.createEventLink("pageaction", context);
		//如果context参数为空，就禁用a链接，由于没有disabled属性，把href设置为#即可。
		writer.element("a",
				"href", null != context ? link : "#" );
		writer.write(text);
		writer.end();
	}
	
	/**
	 * 处理多个参数的传递，目标页是第一个参数，其他参数按原顺序加在目标页后面。
	 * @param srcArray
	 * @param data
	 * @return
	 */
	public Object[] updateArray(Object[] srcArray, Object data) {
		Object[] destArray = new Object[srcArray.length+1];
		destArray[0] = data;
		System.arraycopy(srcArray, 0, destArray, 1, srcArray.length);
		return destArray;
	}

	
	@OnEvent(value="pageaction")
	void onPageAction(EventContext context){
		selectedPage = context.get(Integer.class, 0);
		this.eventContext = context;
	}
	
	/**
	 * 获取第index下标个参数对象。
	 * @param <T>
	 * @param clazz
	 * @param index
	 * @return
	 */
	public <T> T getRequestParameter(Class<T> clazz,int index){
		if(eventContext == null || index>= eventContext.getCount())
			return null;
		return this.eventContext.get(clazz, index);
	}
	
	/**
	 * 获取要跳转的页面。
	 * @return
	 */
	public Pageable getSelectedPage() {
		if(selectedPage == 0)
			return new QPageRequest(selectedPage, pageSize);
		return new QPageRequest(selectedPage-1, pageSize);
	}
	
}
