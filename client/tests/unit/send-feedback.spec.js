import { shallowMount } from "@vue/test-utils";
import SendFeedback from "@/components/SendFeedback";
import CategoryDataService from "../../src/services/CategoryDataService";

jest.mock("../../src/services/CategoryDataService");

describe("SendFeedback.vue Test", () => {
    let wrapper = null;

    beforeEach(() => {
        const responseGet = {
            data: [
                {
                    type: "cat1",
                },
                {
                    type: "cat2",
                },
            ],
        };

        CategoryDataService.getAll.mockResolvedValue(responseGet);

        wrapper = shallowMount(SendFeedback, {
            data() {
                return {
                    username: "",
                    email: "",
                    text: "",
                    selectedCategories: [],
                };
            },
        });
    });

    afterEach(() => {
        wrapper.destroy();
        jest.resetModules();
        jest.clearAllMocks();
    });

    it("Initializes with correct elements", async () => {
        expect(CategoryDataService.getAll).toHaveBeenCalledTimes(1);

        expect(wrapper.findAll("form").length).toEqual(1);

        expect(wrapper.findAll("select").length).toEqual(1);
        expect(wrapper.findAll("option").length).toEqual(2);

        expect(wrapper.findAll("input").length).toEqual(3);
        expect(wrapper.findAll("textarea").length).toEqual(1);
        expect(wrapper.findAll('input[type="submit"]').length).toEqual(1);
    });

    it("Form with empty inputs not valid", async () => {
        const form = await wrapper.find("form").element;
        await expect(form.checkValidity()).toBeFalsy();
    });

    it("Submit with invalid email", async () => {
        await wrapper.find('[name="username"]').setValue("username");
        await wrapper.find('[name="email"]').setValue("invalid");
        await wrapper.find('[name="text"]').setValue("text Something whatever");

        const options = await wrapper.find("select").findAll("option");

        await options.at(1).setSelected();

        const form = await wrapper.find("form").element;
        await expect(form.checkValidity()).toBeFalsy();
    });

    it("Submit with invalid username", async () => {
        await wrapper.find('[name="username"]').setValue("");
        await wrapper.find('[name="email"]').setValue("user@gmail.com");
        await wrapper.find('[name="text"]').setValue("text Something whatever");

        const options = await wrapper.find("select").findAll("option");

        await options.at(1).setSelected();

        const form = await wrapper.find("form").element;
        await expect(form.checkValidity()).toBeFalsy();
    });

    it("Submit with invalid text", async () => {
        await wrapper.find('[name="username"]').setValue("username");
        await wrapper.find('[name="email"]').setValue("user@gmail.com");
        await wrapper.find('[name="text"]').setValue("");

        const options = await wrapper.find("select").findAll("option");

        await options.at(1).setSelected();

        const form = await wrapper.find("form").element;
        await expect(form.checkValidity()).toBeFalsy();
    });

    it("Submit with invalid no option chosen", async () => {
        await wrapper.find('[name="username"]').setValue("username");
        await wrapper.find('[name="email"]').setValue("user@gmail.com");
        await wrapper.find('[name="text"]').setValue("text Something whatever");

        const form = await wrapper.find("form").element;
        await expect(form.checkValidity()).toBeFalsy();
    });

    it("Submit form valid", async () => {
        await wrapper.find('[name="username"]').setValue("username");
        await wrapper.find('[name="email"]').setValue("user@gmail.com");
        await wrapper.find('[name="text"]').setValue("text Something whatever");

        const options = await wrapper.find("select").findAll("option");

        await options.at(1).setSelected();

        const form = await wrapper.find("form").element;

        await expect(form.checkValidity()).toBeTruthy();
    });
});
