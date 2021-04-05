import { shallowMount } from "@vue/test-utils";
import FeedbackList from "@/components/FeedbackList";
import FeedbackDataService from "../../src/services/FeedbackDataService";

jest.mock("../../src/services/FeedbackDataService");

describe("FeedbackList.vue Test", () => {
    let wrapper = null;
    let mockedPagination = null;

    beforeEach(() => {
        const responseGet = {
            data: {
                content: [
                    {
                        text: "text",
                        email: "minueeemail@gmail.com",
                        username: "kasutaja",
                        categories: [],
                    },
                ],
                last: false,
            },
        };

        // scrollTo isn't implemented in JSDOM
        Element.prototype.scrollTo = () => {};

        mockedPagination = FeedbackDataService.getPaginated
            .mockResolvedValueOnce(responseGet)
            .mockResolvedValueOnce({
                data: { ...responseGet.data, last: true },
            });

        wrapper = shallowMount(FeedbackList, {
            data() {
                return {
                    feedbacks: [],
                    page: 0,
                    size: 5,
                    donePaginating: false,
                };
            },
        });
    });

    afterEach(() => {
        wrapper.destroy();
        jest.resetModules();
        jest.clearAllMocks();
        mockedPagination.mockReset();
    });

    it("Initalizes with correct elements", async () => {
        await expect(FeedbackDataService.getPaginated).toHaveBeenCalledTimes(1);

        await expect(wrapper.findAll("table").length).toEqual(1);
        await expect(wrapper.findAll("tbody").length).toEqual(1);
        await expect(wrapper.findAll("tr").length).toEqual(2);
        await expect(wrapper.findAll("button").length).toEqual(1);
    });

    it("Get last page of pagination expect load more button to disappear", async () => {
        const btn = await wrapper.findAll("button");

        await btn.trigger("click");

        expect(wrapper.findAll("tr").length).toEqual(3);

        expect(wrapper.findAll("button").length).toEqual(0);
    });
});
