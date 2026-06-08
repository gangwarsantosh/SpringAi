#!/bin/bash

# English Teaching AI Assistant - API Test Script
# Test all 18 implemented endpoints

BASE_URL="http://localhost:8080/api"
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

echo -e "${YELLOW}========================================${NC}"
echo -e "${YELLOW}English Teaching AI - API Test Suite${NC}"
echo -e "${YELLOW}========================================${NC}"
echo ""

# Test function
test_endpoint() {
    local method=$1
    local endpoint=$2
    local data=$3
    local name=$4

    echo -e "${YELLOW}Testing:${NC} $name"
    echo -e "${YELLOW}$method $BASE_URL$endpoint${NC}"
    
    if [ "$method" = "GET" ]; then
        response=$(curl -s -w "\n%{http_code}" "$BASE_URL$endpoint")
    else
        response=$(curl -s -w "\n%{http_code}" -X "$method" \
            -H "Content-Type: application/json" \
            -d "$data" \
            "$BASE_URL$endpoint")
    fi
    
    http_code=$(echo "$response" | tail -1)
    body=$(echo "$response" | head -n -1)
    
    if [[ $http_code == 200 ]] || [[ $http_code == 201 ]]; then
        echo -e "${GREEN}✓ Status: $http_code${NC}"
    else
        echo -e "${RED}✗ Status: $http_code${NC}"
    fi
    
    echo "Response: $(echo $body | head -c 100)..."
    echo ""
}

# 1. Chat Endpoints
echo -e "${YELLOW}1. CHAT ENDPOINTS${NC}"
test_endpoint "POST" "/chat/send" '{"userId":1,"message":"Hello","conversationId":1}' "Send Message"
test_endpoint "GET" "/chat/history/1" "" "Get Chat History"

# 2. User Endpoints
echo -e "${YELLOW}2. USER ENDPOINTS${NC}"
test_endpoint "GET" "/user/profile?userId=1" "" "Get Profile"
test_endpoint "PUT" "/user/profile?userId=1" '{"name":"John Doe"}' "Update Profile"

# 3. Analytics Endpoints
echo -e "${YELLOW}3. ANALYTICS ENDPOINTS${NC}"
test_endpoint "GET" "/analytics/stats?userId=1" "" "Get Skill Stats"
test_endpoint "GET" "/analytics/daily?userId=1" "" "Get Daily Stats"
test_endpoint "GET" "/analytics/progress?userId=1&timeRange=week" "" "Get Progress"

# 4. Vocabulary Endpoints
echo -e "${YELLOW}4. VOCABULARY ENDPOINTS${NC}"
test_endpoint "GET" "/vocabulary/list" "" "Get Vocabulary List"
test_endpoint "GET" "/vocabulary/search?query=communicate" "" "Search Vocabulary"
test_endpoint "POST" "/vocabulary/save" '{"userId":1,"wordId":1}' "Save Word"
test_endpoint "DELETE" "/vocabulary/remove/1?userId=1" "" "Remove Word"

# 5. Speaking Practice Endpoints
echo -e "${YELLOW}5. SPEAKING PRACTICE ENDPOINTS${NC}"
test_endpoint "GET" "/speaking/scenarios?difficulty=INTERMEDIATE" "" "Get Scenarios"
test_endpoint "POST" "/speaking/session/start" '{"userId":1,"scenarioId":1}' "Start Session"
test_endpoint "POST" "/speaking/session/end" '{"sessionId":42}' "End Session"

# 6. Grammar Endpoints
echo -e "${YELLOW}6. GRAMMAR ENDPOINTS${NC}"
test_endpoint "GET" "/grammar/corrections" "" "Get Corrections"
test_endpoint "POST" "/grammar/check" '{"text":"She go to school"}' "Check Grammar"

# 7. Achievements Endpoints
echo -e "${YELLOW}7. ACHIEVEMENT ENDPOINTS${NC}"
test_endpoint "GET" "/achievements/list?userId=1" "" "Get Achievements"

echo -e "${GREEN}========================================${NC}"
echo -e "${GREEN}Test Suite Completed${NC}"
echo -e "${GREEN}========================================${NC}"
