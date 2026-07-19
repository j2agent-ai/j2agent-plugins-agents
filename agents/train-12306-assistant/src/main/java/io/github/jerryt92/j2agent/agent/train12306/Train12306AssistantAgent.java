package io.github.jerryt92.j2agent.agent.train12306;

import io.github.jerryt92.j2agent.agent.train12306.prompts.Train12306AssistantPrompts;
import io.github.jerryt92.j2agent.service.llm.agent.inf.AiAgent;
import io.github.jerryt92.j2agent.service.llm.agent.inf.constant.AgentThinkingOverride;
import io.github.jerryt92.j2agent.service.llm.agent.inf.feature.ExternalSkills;
import io.github.jerryt92.j2agent.service.llm.agent.inf.feature.McpFeature;
import io.github.jerryt92.j2agent.tools.MathTool;
import io.github.jerryt92.j2agent.tools.WebTool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * 12306 火车信息助手 Agent
 */
@Slf4j
@Component
public class Train12306AssistantAgent extends AiAgent implements ExternalSkills, McpFeature {
    private final MathTool mathTool;
    private final WebTool webTool;

    @Override
    public String getAgentId() {
        return "train_12306_assistant";
    }

    @Override
    public String getAgentName() {
        return "12306火车信息助手";
    }

    @Override
    public String getAgentDescription() {
        return "查询火车车次、余票、经停站与换乘方案等信息";
    }

    @Override
    public String getDispatchPrompt() {
        return """
                J2Agent 12306 火车信息助手；查询车次、余票、经停站与换乘方案，辅以数学计算与网页检索。
                典型问法：查某日两站间高铁余票、某车次经停站、换乘方案等火车出行相关问题。""";
    }

    @Override
    public String loadSystemPrompt() {
        return Train12306AssistantPrompts.SYSTEM_PROMPT;
    }

    @Override
    public int getSort() {
        return 3;
    }

    @Override
    public String getLogo() {
        return "🚄";
    }

    @Override
    public boolean isQaTemplateEnabled() {
        return true;
    }

    @Override
    public AgentThinkingOverride getThinkingOverride() {
        return AgentThinkingOverride.PROVIDER_DEFAULT;
    }

    /** 仅接入 12306 MCP，不合并平台其它 MCP Server */
    @Override
    public boolean useAllMcpServers() {
        return false;
    }

    @Override
    public Set<String> useMcpServers() {
        return Set.of("12306-mcp");
    }

    public Train12306AssistantAgent(MathTool mathTool, WebTool webTool) {
        this.mathTool = mathTool;
        this.webTool = webTool;
    }

    @Override
    protected Object[] buildTools() {
        return new Object[]{mathTool, webTool};
    }
}
